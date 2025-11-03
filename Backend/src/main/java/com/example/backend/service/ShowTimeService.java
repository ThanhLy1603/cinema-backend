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
    //GET ALL
    public List<ShowTime> getAll(){
        return repository.findAll();
    }

    public Optional<ShowTime> getById(UUID id){
        return repository.findById(id);
    }
    //POST
    public ShowTime create(ShowTime showTime) {
        LocalTime newTime = showTime.getStartTime();
        // Kiểm tra giờ đã tồn tại
        boolean exists = repository.findAll().stream()
                .anyMatch(st -> st.getStartTime().equals(newTime) && !st.getIsDeleted());
        if (exists) {
            throw new RuntimeException("Giờ chiếu " + newTime + " đã tồn tại. Hãy nhập giờ khác.");
        }

        showTime.setIsDeleted(false);
        return repository.saveAndFlush(showTime);
    }
    //PUT
    public ShowTime update(UUID id, ShowTime data) {
        ShowTime showTime = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giờ chiếu."));

        LocalTime newTime = data.getStartTime();

        boolean duplicate = repository.findAll().stream()
                .anyMatch(st -> !st.getId().equals(id)
                        && st.getStartTime().equals(newTime)
                        && !st.getIsDeleted());

        if (duplicate) {
            throw new RuntimeException("Giờ chiếu " + newTime + " đã tồn tại. Hãy nhập giờ khác.");
        }
        showTime.setStartTime(newTime);
        showTime.setIsDeleted(data.getIsDeleted());
        return repository.saveAndFlush(showTime);
    }
    //DELETE
    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
