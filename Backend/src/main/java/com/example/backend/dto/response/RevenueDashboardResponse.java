package com.example.backend.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record RevenueDashboardResponse(
        BigDecimal totalRevenue,
        BigDecimal todayRevenue,
        BigDecimal monthRevenue,
        BigDecimal yearRevenue,
        List<Object[]> dailyRevenue
) {
}
