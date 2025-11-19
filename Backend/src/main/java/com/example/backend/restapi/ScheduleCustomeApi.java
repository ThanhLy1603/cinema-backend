package com.example.backend.restapi;


import com.example.backend.dto.ScheduleManageResponse;
import com.example.backend.service.ScheduleManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleCustomeApi {
    private final ScheduleManageService scheduleManageService;


    @GetMapping("/{filmid}")
    public ResponseEntity<List<ScheduleManageResponse>> getAllSchedulesByFilmId(@PathVariable UUID filmid) {
        return ResponseEntity.ok(scheduleManageService.getSchedulesByFilmId(filmid));
    }
}
