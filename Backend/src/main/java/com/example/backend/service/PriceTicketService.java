package com.example.backend.service;

import com.example.backend.dto.response.PriceTicketResponse;
import com.example.backend.entity.PriceTicket;
import com.example.backend.repository.PriceTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceTicketService {
    private final PriceTicketRepository priceTicketRepository;

    public List<PriceTicketResponse> getPriceTicketByDate(UUID filmId, UUID showTimeId, LocalDate date) {
        List<PriceTicketResponse> priceTickets = priceTicketRepository
                .findByFilmIdAndShowTimeIdAndStartDateAndIsDeletedFalse(filmId, showTimeId, date)
                .stream()
                .map(this::toPriceTicketResponse)
                .collect(Collectors.toList());

        System.out.println("priceTickets: " + priceTickets);
        return priceTickets;
    }

    private PriceTicketResponse toPriceTicketResponse(PriceTicket priceTicket) {
        return new PriceTicketResponse(
                priceTicket.getId(),
                priceTicket.getFilm().getId(),
                priceTicket.getSeatType().getName(),
                priceTicket.getPrice(),
                priceTicket.getStartDate(),
                priceTicket.getEndDate()
        );
    }
}
