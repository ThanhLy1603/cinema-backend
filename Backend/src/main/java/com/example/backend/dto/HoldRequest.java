package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HoldRequest {
    private String holderId;
    private Integer holdMinutes; // optional
}
