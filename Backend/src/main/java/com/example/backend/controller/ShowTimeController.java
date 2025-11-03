package com.example.backend.controller;

import com.example.backend.dto.ShowTimeRequest;
import com.example.backend.dto.ShowTimeResponse;
import com.example.backend.entity.ShowTime;
import com.example.backend.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/showtimes")
public class ShowTimeController {

    @Autowired
    private ShowTimeService service;

    //GET all
    @GetMapping
    public ResponseEntity<List<ShowTimeResponse>> getAll() {
        List<ShowTimeResponse> list = service.getAll()
                .stream()
                .map(st -> new ShowTimeResponse(st.getStartTime(), st.getIsDeleted()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    //GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<ShowTimeResponse> getById(@PathVariable UUID id) {
        return service.getById(id)
                .map(st -> ResponseEntity.ok(new ShowTimeResponse(st.getStartTime(), st.getIsDeleted())))
                .orElse(ResponseEntity.notFound().build());
    }

    //POST
    @PostMapping
    public ResponseEntity<ShowTimeResponse> create(@RequestBody ShowTimeRequest request) {
        ShowTime showTime = new ShowTime();
        showTime.setStartTime(request.startTime());
        showTime.setIsDeleted(request.isDeleted() != null ? request.isDeleted() : false);

        ShowTime created = service.create(showTime);
        return ResponseEntity.ok(new ShowTimeResponse(created.getStartTime(), created.getIsDeleted()));
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<ShowTimeResponse> update(@PathVariable UUID id, @RequestBody ShowTimeRequest request) {
        ShowTime showTime = new ShowTime();
        showTime.setStartTime(request.startTime());
        showTime.setIsDeleted(request.isDeleted());

        ShowTime updated = service.update(id, showTime);
        return ResponseEntity.ok(new ShowTimeResponse(updated.getStartTime(), updated.getIsDeleted()));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
