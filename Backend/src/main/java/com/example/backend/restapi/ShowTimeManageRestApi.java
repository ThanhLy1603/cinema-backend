package com.example.backend.restapi;

import com.example.backend.dto.ShowTimeManageRequest;
import com.example.backend.dto.ShowTimeManageResponse;
import com.example.backend.entity.ShowTime;
import com.example.backend.service.ShowTimeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/showtimes")
public class ShowTimeManageRestApi {

    private final ShowTimeService service;

    public ShowTimeManageRestApi(ShowTimeService service) {
        this.service = service;
    }

    @GetMapping
    public List<ShowTimeManageResponse> getAll() {
        return service.getAll().stream()
                .map(st -> new ShowTimeManageResponse(st.getId(), st.getStartTime()))
                .toList();
    }

    @PostMapping
    public ShowTimeManageResponse create(@RequestBody ShowTimeManageRequest request) {
        ShowTime showTime = new ShowTime();
        showTime.setStartTime(request.startTime());
        ShowTime created = service.create(showTime);
        return new ShowTimeManageResponse(created.getId(), created.getStartTime());
    }

    @DeleteMapping("/{id}")
    public void softDelete(@PathVariable UUID id) {
        service.delete(id);
    }
}
