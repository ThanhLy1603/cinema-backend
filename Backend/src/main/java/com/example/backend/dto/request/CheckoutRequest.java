package com.example.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CheckoutRequest {
    private String holderId;
    private List<UUID> seatIds;
}
