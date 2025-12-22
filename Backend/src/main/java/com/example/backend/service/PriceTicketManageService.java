package com.example.backend.service;

import com.example.backend.dto.request.PriceTicketManageRequest;
import com.example.backend.dto.response.*;
import com.example.backend.entity.Film;
import com.example.backend.entity.PriceTicket;
import com.example.backend.entity.SeatType;
import com.example.backend.entity.ShowTime;
import com.example.backend.repository.FilmRepository;
import com.example.backend.repository.PriceTicketRepository;
import com.example.backend.repository.SeatTypeRepository;
import com.example.backend.repository.ShowTimeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PriceTicketManageService {

    private final PriceTicketRepository priceTicketRepository;
    private final FilmRepository filmRepository;
    private final SeatTypeRepository seatTypeRepository;
    private final ShowTimeRepository showTimeRepository;

    @Transactional
    public List<PriceTicketManageResponse> getAllPriceTickets() {
        return priceTicketRepository.findAllByIsDeletedFalse().stream()
                .map(this::toPriceTicketManageResponse)
                .toList();
    }

    @Transactional
    public ApiResponse bulkPriceTickets(List<PriceTicketManageRequest> requests) {
        var entities = requests.stream()
                .map(this::toEntity)
                .toList();

        priceTicketRepository.saveAll(entities);
        return new ApiResponse("success", "Thêm giá vé thành công");
    }

    @Transactional
    public ApiResponse createPriceTicket(PriceTicketManageRequest request) {
        PriceTicket entity = toEntity(request);
        priceTicketRepository.save(entity);
        return new ApiResponse("success", "Tạo giá vé thành công");
    }

    @Transactional
    public ApiResponse updatePriceTicket(UUID id, PriceTicketManageRequest request) {
        return priceTicketRepository.findById(id)
                .map(existing -> {
                    updateEntity(existing, request);
                    priceTicketRepository.save(existing);
                    return new ApiResponse("success", "Cập nhật giá vé thành công");
                })
                .orElse(new ApiResponse("error", "Không tìm thấy giá vé"));
    }

    @Transactional
    public ApiResponse deletePriceTicket(UUID id) {
        return priceTicketRepository.findById(id)
                .map(priceTicket -> {
                    priceTicket.setIsDeleted(true);
                    priceTicketRepository.save(priceTicket);
                    return new ApiResponse("success", "Xóa giá vé thành công");
                })
                .orElse(new ApiResponse("error", "Không tìm thấy giá vé"));
    }

    // ==================== PRIVATE HELPERS ====================

    private PriceTicket toEntity(PriceTicketManageRequest r) {
        Film film = filmRepository.getReferenceById(r.filmId());
        SeatType seatType = seatTypeRepository.getReferenceById(r.seatTypeId());
        ShowTime showTime = showTimeRepository.getReferenceById(r.showTimeId());

        return PriceTicket.builder()
                .film(film)
                .seatType(seatType)
                .showTime(showTime)
                .dayType(PriceTicket.DayType.valueOf(r.dayType()))
                .price(r.price() != null ? r.price() : BigDecimal.ZERO)
                .startDate(r.startDate())
                .endDate(r.endDate())
                .isDeleted(false)
                .build();
    }

    private void updateEntity(PriceTicket existing, PriceTicketManageRequest r) {
        Film film = filmRepository.getReferenceById(r.filmId());
        SeatType seatType = seatTypeRepository.getReferenceById(r.seatTypeId());
        ShowTime showTime = showTimeRepository.getReferenceById(r.showTimeId());

        existing.setFilm(film);
        existing.setSeatType(seatType);
        existing.setShowTime(showTime);
        existing.setDayType(PriceTicket.DayType.valueOf(r.dayType()));
        existing.setPrice(r.price());
        existing.setStartDate(r.startDate());
        existing.setEndDate(r.endDate());
    }

    private PriceTicketManageResponse toPriceTicketManageResponse(PriceTicket pt) {
        return new PriceTicketManageResponse(
                pt.getId(),
                toFilmResponse(pt.getFilm()),
                toSeatTypeResponse(pt.getSeatType()),
                toShowTimeResponse(pt.getShowTime()),
                pt.getDayType().name(),
                pt.getPrice(),
                pt.getStartDate(),
                pt.getEndDate()
        );
    }

    private FilmResponse toFilmResponse(Film film) {
        return new FilmResponse(
                film.getId(), film.getName(), film.getCountry(), film.getDirector(),
                film.getActor(), film.getDescription(), film.getDuration(),
                film.getPoster(), film.getTrailer(), film.getReleaseDate(), film.getStatus()
        );
    }

    private SeatTypeManageResponse toSeatTypeResponse(SeatType seatType) {
        return new SeatTypeManageResponse(seatType.getId(), seatType.getName());
    }

    private ShowTimeManageResponse toShowTimeResponse(ShowTime showTime) {
        return new ShowTimeManageResponse(showTime.getId(), showTime.getStartTime());
    }
}