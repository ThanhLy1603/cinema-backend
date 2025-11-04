package com.example.backend.restapi;

import com.example.backend.controller.ShowTimeManageController;
import com.example.backend.dto.ShowTimeManageRequest;
import com.example.backend.dto.ShowTimeManageResponse;
import com.example.backend.entity.ShowTime;
import com.example.backend.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ShowTimeManageRestApi implements ShowTimeManageController {

    @Autowired
    private ShowTimeService service;

    @Override
    public ResponseEntity<List<ShowTimeManageResponse>> getAll() {
        List<ShowTimeManageResponse> list = service.getAll()
                .stream()
                .map(st -> new ShowTimeManageResponse(st.getId(), st.getStartTime(), st.getIsDeleted()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<ShowTimeManageResponse> getById(UUID id) {
        return service.getById(id)
                .map(st -> ResponseEntity.ok(new ShowTimeManageResponse(st.getId(), st.getStartTime(),st.getIsDeleted())))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ShowTimeManageResponse> create(ShowTimeManageRequest request) {
        ShowTime showTime = new ShowTime();
        showTime.setStartTime(request.startTime());

        ShowTime created = service.create(showTime);
        return ResponseEntity.ok(new ShowTimeManageResponse(created.getId(), created.getStartTime(),created.getIsDeleted()));
    }

    @Override
    public ResponseEntity<ShowTimeManageResponse> update(UUID id, ShowTimeManageRequest request) {
        ShowTime showTime = new ShowTime();
        showTime.setStartTime(request.startTime());

        ShowTime updated = service.update(id, showTime);
        return ResponseEntity.ok(new ShowTimeManageResponse(updated.getId(), updated.getStartTime(),updated.getIsDeleted()));
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateStatus(UUID id, boolean isDeleted) {
        service.updateStatus(id, isDeleted);
        return ResponseEntity.noContent().build();
    }
}
