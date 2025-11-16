package com.example.backend.repository;

import com.example.backend.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, UUID> {
}
