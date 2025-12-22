package com.example.backend.restapi;

import com.example.backend.dto.request.ScanQrRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.dto.response.ScanQrResponse;
import com.example.backend.service.QrScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/staff/qr")
@RequiredArgsConstructor
public class QrScanRestApi {
    private final QrScanService qrScanService;

    @PostMapping("/scan")
    public ResponseEntity<ScanQrResponse> scanQrCode(@RequestBody ScanQrRequest request){
        return ResponseEntity.ok(qrScanService.scanQr(request));
    }

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse> scanQRCode(@RequestBody ScanQrRequest request){
        return ResponseEntity.ok(qrScanService.confirmQRCode(request));
    }
}
