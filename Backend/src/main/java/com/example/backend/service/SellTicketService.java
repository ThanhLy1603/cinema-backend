package com.example.backend.service;

import com.example.backend.dto.request.CheckoutRequest;
import com.example.backend.dto.request.StaffSellTicketRequest;
import com.example.backend.dto.request.StaffTicketItem;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellTicketService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final SeatRepository seatRepository;
    private final PriceTicketRepository priceTicketRepository;
    private final InvoiceTicketRepository invoiceTicketRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceQRCodeRepository invoiceQRCodeRepository;
    private final SeatReservationService seatReservationService;
    private final QRCodeService qrCodeService;

    @Transactional
    public ApiResponse sellTicket(StaffSellTicketRequest staffSellTicketRequest) {
        System.out.println("sellTicket: " + staffSellTicketRequest);

        UUID scheduleId = staffSellTicketRequest.tickets().getFirst().scheduleId();
        Users user = userRepository.findByUsername(staffSellTicketRequest.invoice().username()).orElse(null);

        if (user == null) return new ApiResponse("fail", "Không tìm thấy Username");

        // Tạo đối tượng invoice
        Invoice invoice = new Invoice();
        invoice.setUsername(user);
        invoice.setCreatedBy(user);
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setCustomerAddress(staffSellTicketRequest.invoice().customerAddress());
        invoice.setCustomerName(staffSellTicketRequest.invoice().customerName());
        invoice.setCustomerPhone(staffSellTicketRequest.invoice().customerPhone());
        invoice.setTotalAmount(staffSellTicketRequest.invoice().totalAmount());
        invoice.setDiscountAmount(staffSellTicketRequest.invoice().discount());
        invoice.setFinalAmount(staffSellTicketRequest.invoice().finalAmount());
        invoice.setStatus("PENDING");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        System.out.println(" saved invoice: " + savedInvoice);

        // Tạo đối tượng invoice ticket
        for (StaffTicketItem ticketItem : staffSellTicketRequest.tickets()) {
            Schedule schedule = scheduleRepository.findById(ticketItem.scheduleId()).orElse(null);
            Seat seat = seatRepository.findById(ticketItem.seatId()).orElse(null);
            PriceTicket priceTicket = priceTicketRepository.findById(ticketItem.ticketPriceId()).orElse(null);

            if (seat == null) return new ApiResponse("fail", "Không tìm thấy ghế ngồi");
            if (priceTicket == null) return new ApiResponse("fail", "Không tìm thấy giá vé");
            if (schedule == null) return new ApiResponse("fail", "Không tìm thấy lịch chiếu");

            InvoiceTicket invoiceTicket = new InvoiceTicket();
            invoiceTicket.setInvoice(savedInvoice);
            invoiceTicket.setSchedule(schedule);
            invoiceTicket.setSeat(seat);
            invoiceTicket.setTicketPrice(priceTicket);
            invoiceTicket.setPrice(priceTicket.getPrice());
            InvoiceTicket savedInvoiceTicket = invoiceTicketRepository.save(invoiceTicket);

            System.out.println("saved invoiceTicket: " + savedInvoiceTicket.getPrice());
        }

        // Tạo QR code
        String qrString = qrCodeService.generateQRCode(savedInvoice.getId());

        InvoiceQRCode invoiceQRCode = new InvoiceQRCode();
        invoiceQRCode.setInvoice(savedInvoice);
        invoiceQRCode.setQrCode(qrString);
        invoiceQRCode.setQrType("TICKET");
        invoiceQRCode.setIsUsed(false);
        invoiceQRCodeRepository.save(invoiceQRCode);

        // Xử lý checkout
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setHolderId(staffSellTicketRequest.invoice().username());
        checkoutRequest.setSeatIds(
                staffSellTicketRequest.tickets().stream()
                .map(StaffTicketItem::seatId)
                .toList()
        );

        seatReservationService.checkout(scheduleId, checkoutRequest);

        System.out.println("schedule id: " + scheduleId);
        System.out.println("checkoutRequest: " + checkoutRequest.getSeatIds() + " " + checkoutRequest.getHolderId());

        return new ApiResponse("success", "Tạo hoá đơn thành công");
    }
}
