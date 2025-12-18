package com.example.backend.repository;

import com.example.backend.entity.InvoiceQRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceQRCodeRepository extends JpaRepository<InvoiceQRCode, UUID> {
    List<InvoiceQRCode> findByInvoiceId(UUID invoiceId);

    InvoiceQRCode findByQrCode(String qrCode);
}
