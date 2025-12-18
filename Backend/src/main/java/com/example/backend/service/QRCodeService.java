package com.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QRCodeService {

    @Value("${qr.secret}")
    private String qrSecret;

    public String generateQRCode(UUID invoiceId) {
        String raw = invoiceId + "|" + qrSecret;
        return Base64.getEncoder().encodeToString(raw.getBytes());
    }

    public UUID verifyAndExtractQRCode(String qrCode) {
        try {
            String decoded = new String(Base64.getDecoder().decode(qrCode));
            String[] parts = decoded.trim().split("/");

            if (parts.length != 2 || !parts[1].equals(qrSecret)) {
                throw new Exception("QR code giả");
            }

            return UUID.fromString(parts[0]);
        } catch (Exception e) {
            throw new RuntimeException("QR code không phù hợp");
        }
    }
}
