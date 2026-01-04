package com.example.backend.repository;

import com.example.backend.entity.InvoiceTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceTicketRepository extends JpaRepository<InvoiceTicket, UUID> {
    List<InvoiceTicket> findByInvoiceId(UUID invoiceId);
}
