package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.ShowTimeManageRequest;
import com.example.backend.dto.ShowTimeManageResponse;
import com.example.backend.entity.ShowTime;
import com.example.backend.repository.ShowTimeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowTimeService {

    private final ShowTimeRepository repository;

    //LẤY GIỜ CHƯA ẨN
    @Transactional
    public List<ShowTimeManageResponse> getAll() {
        List<ShowTimeManageResponse> showTimes = repository.findByIsDeletedFalseOrderByStartTimeAsc().stream()
                .map(this::toShowTimeResponse)
                .collect(Collectors.toList());
        return showTimes;
    }

    // THÊM GIỜ CHIẾU
    @Transactional
    public ApiResponse create(ShowTimeManageRequest request) {
        LocalTime newTime = request.startTime();
        if (newTime == null) {
            throw new RuntimeException("Giờ chiếu không được để trống!");
        }

        if (newTime.isBefore(LocalTime.of(8, 0)) || newTime.isAfter(LocalTime.of(23, 59))) {
            throw new RuntimeException("Giờ chiếu phải nằm trong khoảng 08:00 - 24:00!");
        }

        boolean exists = repository.findByIsDeletedFalseOrderByStartTimeAsc().stream()
                .anyMatch(st -> st.getStartTime()
                        .truncatedTo(ChronoUnit.MINUTES)
                        .equals(newTime.truncatedTo(ChronoUnit.MINUTES)));

        if (exists) {
            throw new RuntimeException("Giờ chiếu " + newTime + " đã tồn tại!");
        }

        // ✅ Tạo entity ShowTime mới
        ShowTime showTime = new ShowTime();
        showTime.setStartTime(newTime);
        showTime.setIsDeleted(false);

        repository.save(showTime);

        return new ApiResponse("success", "Thêm giờ chiếu thành công");
    }

    // SOFT DELETE (ẩn, không xóa dữ liệu thật)
    @Transactional
    public ApiResponse delete(UUID id) {
        ShowTime showTime = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giờ chiếu!"));
        showTime.setIsDeleted(true);
        repository.saveAndFlush(showTime);
        return new ApiResponse("success", "Xoá giờ thành công");
    }

    private ShowTimeManageResponse toShowTimeResponse(ShowTime showTime) {
        return new ShowTimeManageResponse(
                showTime.getId(),
                showTime.getStartTime()
        );
    }
}
