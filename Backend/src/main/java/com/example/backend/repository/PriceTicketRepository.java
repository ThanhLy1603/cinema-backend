package com.example.backend.repository;

import com.example.backend.entity.PriceTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceTicketRepository extends JpaRepository<PriceTicket, UUID> {

    // Lấy tất cả giá vé chưa bị xóa
    List<PriceTicket> findAllByIsDeletedFalse();

    // Đếm số lượng theo ngày bắt đầu (nếu cần)
    long countByStartDate(LocalDate startDate);

    // Lấy 1 bản ghi bất kỳ theo film + seatType (thường dùng cho fallback)
    Optional<PriceTicket> findTopByFilmIdAndSeatTypeId(UUID filmId, UUID seatTypeId);

    // ---------------------------------------------------------------------
    // 1. Kiểm tra trùng khi TẠO MỚI → dùng @Query (tránh lỗi thứ tự tham số)
    // ---------------------------------------------------------------------
    @Query("""
        SELECT COUNT(pt) > 0 FROM PriceTicket pt
        WHERE pt.film.id = :filmId
          AND pt.seatType.id = :seatTypeId
          AND pt.showTime.id = :showTimeId
          AND pt.dayType = :dayType
          AND pt.startDate = :startDate
          AND pt.isDeleted = false
        """)
    boolean existsByFilmIdAndSeatTypeIdAndShowTimeIdAndDayTypeAndStartDate(
            @Param("filmId") UUID filmId,
            @Param("seatTypeId") UUID seatTypeId,
            @Param("showTimeId") UUID showTimeId,
            @Param("dayType") PriceTicket.DayType dayType,
            @Param("startDate") LocalDate startDate);

    // ---------------------------------------------------------------------
    // 2. Kiểm tra trùng khi CẬP NHẬT (loại trừ chính nó)
    // ---------------------------------------------------------------------
    @Query("""
        SELECT COUNT(pt) > 0 FROM PriceTicket pt
        WHERE pt.film.id = :filmId
          AND pt.seatType.id = :seatTypeId
          AND pt.showTime.id = :showTimeId
          AND pt.dayType = :dayType
          AND pt.startDate = :startDate
          AND pt.id <> :id
          AND pt.isDeleted = false
        """)
    boolean existsByFilmIdAndSeatTypeIdAndShowTimeIdAndDayTypeAndStartDateAndIdNot(
            @Param("filmId") UUID filmId,
            @Param("seatTypeId") UUID seatTypeId,
            @Param("showTimeId") UUID showTimeId,
            @Param("dayType") PriceTicket.DayType dayType,
            @Param("startDate") LocalDate startDate,
            @Param("id") UUID id);

    // ---------------------------------------------------------------------
    // 3. Lấy giá vé đang áp dụng cho một lịch chiếu cụ thể (scheduleDate)
    //    Ưu tiên: có endDate = null hoặc endDate >= ngày chiếu
// ---------------------------------------------------------------------
    @Query("""
        SELECT pt FROM PriceTicket pt
        WHERE pt.film.id = :filmId
          AND pt.seatType.id = :seatTypeId
          AND pt.showTime.id = :showTimeId
          AND pt.dayType = :dayType
          AND pt.startDate <= :scheduleDate
          AND (pt.endDate IS NULL OR pt.endDate >= :scheduleDate)
          AND pt.isDeleted = false
        ORDER BY pt.startDate DESC
        """)
    Optional<PriceTicket> findActivePrice(
            @Param("filmId") UUID filmId,
            @Param("seatTypeId") UUID seatTypeId,
            @Param("showTimeId") UUID showTimeId,
            @Param("dayType") PriceTicket.DayType dayType,
            @Param("scheduleDate") LocalDate scheduleDate);

    // ---------------------------------------------------------------------
    // 4. Phương thức cũ bạn đang dùng (giữ lại để không phá code cũ)
    //    Đã sửa điều kiện endDate để chính xác hơn
    // ---------------------------------------------------------------------
    @Query("""
        SELECT pt FROM PriceTicket pt
        WHERE pt.film.id = :filmId
          AND pt.seatType.id = :seatTypeId
          AND pt.showTime.id = :showTimeId
          AND pt.dayType = :dayType
          AND pt.startDate <= :scheduleDate
          AND (pt.endDate IS NULL OR pt.endDate >= :scheduleDate)
          AND pt.isDeleted = false
        ORDER BY pt.startDate DESC
        """)
    Optional<PriceTicket> findTicketPrice(
            @Param("filmId") UUID filmId,
            @Param("seatTypeId") UUID seatTypeId,
            @Param("showTimeId") UUID showTimeId,
            @Param("dayType") PriceTicket.DayType dayType,
            @Param("scheduleDate") LocalDate scheduleDate);
}