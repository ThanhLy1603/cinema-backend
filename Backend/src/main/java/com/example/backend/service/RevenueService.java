package com.example.backend.service;

import com.example.backend.dto.response.RevenueByDateResponse;
import com.example.backend.dto.response.RevenueDashboardResponse;
import com.example.backend.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RevenueService {
    private final InvoiceRepository invoiceRepository;
    public RevenueDashboardResponse getRevenueDashboard() {

        LocalDate today = LocalDate.now();

        return new RevenueDashboardResponse(
                invoiceRepository.totalRevenue(),                         // Tổng doanh thu
                invoiceRepository.todayRevenue(today),                    // Doanh thu hôm nay
                invoiceRepository.monthRevenue(today.getMonthValue(),     // Doanh thu tháng
                        today.getYear()),
                invoiceRepository.yearRevenue(today.getYear()),           // Doanh thu năm
                invoiceRepository.dailyRevenueNative()
        );
    }

    public List<RevenueByDateResponse> getDailyRevenue() {
        return invoiceRepository.dailyRevenueNative()
                .stream()
                .map(row -> new RevenueByDateResponse(
                        ((java.sql.Date) row[0]).toLocalDate(),
                        (BigDecimal) row[1]
                ))
                .toList();
    }



}
