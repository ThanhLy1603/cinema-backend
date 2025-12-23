package com.example.backend.restapi;

import com.example.backend.dto.request.StaffSellProductRequest;
import com.example.backend.dto.request.StaffSellTicketRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.service.SellTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/staff/")
public class StaffSellTicketRestApi {
    private final SellTicketService sellTicketService;

    @PostMapping("/sell-ticket")
    public ResponseEntity<ApiResponse> sellTicket(@RequestBody StaffSellTicketRequest staffSellTicketRequest) {
        return ResponseEntity.ok(sellTicketService.sellTicket(staffSellTicketRequest));
    }

    @PostMapping("/sell-product")
    public ResponseEntity<ApiResponse> sellProduct(@RequestBody StaffSellProductRequest staffSellProductRequest) {
        return ResponseEntity.ok(sellTicketService.sellProduct(staffSellProductRequest));
    }
}
