package com.example.backend.restapi;

import com.example.backend.dto.response.PriceTicketResponse;
import com.example.backend.service.PriceTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/price-tickets")
@RequiredArgsConstructor
public class PriceTicketRestApi {
    private final PriceTicketService priceTicketService;

    @GetMapping
    public ResponseEntity<List<PriceTicketResponse>> getPriceTickets(
            @RequestParam UUID filmId,
            @RequestParam UUID showTimeId,
            @RequestParam LocalDate date
            ){
        return ResponseEntity.ok(priceTicketService.getPriceTicketByDate(filmId, showTimeId, date));
    }
}
