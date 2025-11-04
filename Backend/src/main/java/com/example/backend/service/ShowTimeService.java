package com.example.backend.service;

import com.example.backend.entity.ShowTime;
import com.example.backend.repository.ShowTimeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class ShowTimeService {

    @Autowired
    private ShowTimeRepository repository;
    @Transactional
    //LẤY GIỜ CHƯA ẨN
    public List<ShowTime> getAll() {
        return repository.findByIsDeletedFalseOrderByStartTimeAsc();
    }

    // THÊM GIỜ CHIẾU
    public ShowTime create(ShowTime showTime) {
        LocalTime newTime = showTime.getStartTime();

        if (newTime == null)
            throw new RuntimeException("Giờ chiếu không được để trống!");

        // Nếu giờ chiếu < 8:00 hoặc > 24:00 -> báo lỗi
        if (newTime.isBefore(LocalTime.of(8, 0)) || newTime.isAfter(LocalTime.of(23, 59))) {
            throw new RuntimeException("Giờ chiếu phải nằm trong khoảng 08:00 - 24:00!");
        }

        // Ép kiểu LocalTime sang LocalDateTime giả định (để tránh lỗi khi DB là DATETIME)
        LocalDateTime newDateTime = LocalDateTime.of(LocalDate.of(2000, 1, 1), newTime);

        // Kiểm tra trùng giờ (so sánh giờ và phút, không ngày)
        boolean exists = repository.findAll().stream()
                .filter(st -> !st.getIsDeleted()) // chỉ kiểm tra giờ đang hoạt động
                .anyMatch(st -> st.getStartTime()
                        .truncatedTo(ChronoUnit.MINUTES)
                        .equals(newTime.truncatedTo(ChronoUnit.MINUTES)));

        if (exists)
            throw new RuntimeException("Giờ chiếu " + newTime + " đã tồn tại!");

        showTime.setIsDeleted(false);
        return repository.saveAndFlush(showTime);
    }

    // SOFT DELETE (ẩn, không xóa dữ liệu thật)
    public void delete(UUID id) {
        ShowTime showTime = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giờ chiếu!"));
        showTime.setIsDeleted(true);
        repository.saveAndFlush(showTime);
    }
}
