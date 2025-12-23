package com.example.backend.repository;

import com.example.backend.dto.response.RevenueByDateResponse;
import com.example.backend.entity.Invoice;
import com.example.backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    List<Invoice> findAllByUsernameUsername(String username);

    List<Invoice> findByIsDeletedFalseOrderByCreatedAtDesc();

<<<<<<< HEAD
    // ================== TỔNG DOANH THU ==================
    @Query(value = """
        SELECT COALESCE(SUM(finalamount), 0)
        FROM invoices
        WHERE status = 'PAID'
          AND isdeleted = 0
    """, nativeQuery = true)
    BigDecimal totalRevenue();

    // ================== DOANH THU HÔM NAY ==================
    @Query(value = """
        SELECT COALESCE(SUM(finalamount), 0)
        FROM invoices
        WHERE status = 'PAID'
          AND isdeleted = 0
          AND CAST(createdat AS DATE) = CAST(GETDATE() AS DATE)
    """, nativeQuery = true)
    BigDecimal todayRevenue(LocalDate date);

    // ================== DOANH THU THEO THÁNG ==================
    @Query(value = """
        SELECT COALESCE(SUM(finalamount), 0)
        FROM invoices
        WHERE status = 'PAID'
          AND isdeleted = 0
          AND MONTH(createdat) = :month
          AND YEAR(createdat) = :year
    """, nativeQuery = true)
    BigDecimal monthRevenue(int month, int year);

    // ================== DOANH THU THEO NĂM ==================
    @Query(value = """
        SELECT COALESCE(SUM(finalamount), 0)
        FROM invoices
        WHERE status = 'PAID'
          AND isdeleted = 0
          AND YEAR(createdAt) = :year
    """, nativeQuery = true)
    BigDecimal yearRevenue(int year);

    // ================== DOANH THU THEO NGÀY ==================
    @Query(value = """
        SELECT
            CAST(createdat AS DATE) AS revenuedate,
            SUM(finalamount) AS revenue
        FROM invoices
        WHERE status = 'PAID'
          AND isdeleted = 0
        GROUP BY CAST(createdat AS DATE)
        ORDER BY revenuedate
    """, nativeQuery = true)
    List<Object[]> dailyRevenueNative();
=======
    List<Invoice> findAllByUsername_UsernameOrderByCreatedAtDesc(String username);
>>>>>>> b83be6d0a0e272a01cdec8e9223781ca0c5b6f4f
}

