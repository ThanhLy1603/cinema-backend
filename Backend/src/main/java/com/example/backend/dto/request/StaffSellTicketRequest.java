package com.example.backend.dto.request;

import java.util.List;

public record StaffSellTicketRequest(
        StaffInvoice invoice,
        List<StaffTicketItem> tickets
) {
}
