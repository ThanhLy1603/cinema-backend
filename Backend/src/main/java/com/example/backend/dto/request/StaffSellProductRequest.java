package com.example.backend.dto.request;

import java.util.List;

public record StaffSellProductRequest (
        StaffInvoice invoice,
        List<StaffProductItem> products
) {
}
