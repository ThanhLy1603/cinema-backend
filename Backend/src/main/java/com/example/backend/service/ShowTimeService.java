package com.example.backend.service;

import com.example.backend.entity.ShowTime;
import com.example.backend.repository.ShowTimeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ShowTimeService {
    @Autowired
    private ShowTimeRepository repository;

    // GET ALL
    public List<ShowTime> getAll() {
        return repository.findAll();
    }

    public Optional<ShowTime> getById(UUID id) {
        return repository.findById(id);
    }

    // POST
    public ShowTime create(ShowTime showTime) {
        LocalTime newTime = showTime.getStartTime();

        //Thêm kiểm tra giờ hợp lệ nhưng không đổi luồng cũ
        if (!isValidShowTime(newTime)) {
            throw new RuntimeException("Giờ chiếu phải nằm trong khoảng từ 08:00 đến 23:59.");
        }

        // ⚡ Giữ nguyên logic cũ kiểm tra trùng giờ
        boolean exists = repository.findAll().stream()
                .anyMatch(st -> st.getStartTime().equals(newTime) && !st.getIsDeleted());
        if (exists) {
            throw new RuntimeException("Giờ chiếu " + newTime + " đã tồn tại. Hãy nhập giờ khác.");
        }

        showTime.setIsDeleted(false);
        return repository.saveAndFlush(showTime);
    }

    // PUT
    public ShowTime update(UUID id, ShowTime data) {
        ShowTime showTime = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giờ chiếu."));
        LocalTime newTime = data.getStartTime();
        if (newTime == null) {
            throw new RuntimeException("Giờ chiếu không hợp lệ hoặc bị thiếu.");
        }

        if (!isValidShowTime(newTime)) {
            throw new RuntimeException("Giờ chiếu phải nằm trong khoảng từ 08:00 đến 23:59.");
        }

        boolean duplicate = repository.findAll().stream()
                .anyMatch(st -> !st.getId().equals(id)
                        && st.getStartTime().equals(newTime)
                        && !st.getIsDeleted());

        if (duplicate) {
            throw new RuntimeException("Giờ chiếu " + newTime + " đã tồn tại. Hãy nhập giờ khác.");
        }

        showTime.setStartTime(newTime);
        if (data.getIsDeleted() != null) {
            showTime.setIsDeleted(data.getIsDeleted());
        } else if (showTime.getIsDeleted() == null) {
            showTime.setIsDeleted(false);
        }
        return repository.saveAndFlush(showTime);
    }

    // DELETE
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    //Hàm kiểm tra giờ chiếu hợp lệ (rạp mở từ 08:00 → 24:00)
    private boolean isValidShowTime(LocalTime time) {
        if (time == null) return false;
        LocalTime open = LocalTime.of(8, 0);
        LocalTime close = LocalTime.of(23, 59);
        return !time.isBefore(open) && !time.isAfter(close);
    }

    @Transactional
    public ShowTime updateStatus(UUID id, boolean isDeleted) {
        ShowTime showTime = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giờ chiếu."));

        showTime.setIsDeleted(isDeleted);
        return repository.saveAndFlush(showTime);
    }

}
