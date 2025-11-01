package com.example.backend.service;

import com.example.backend.entity.Category;
import com.example.backend.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepo;


    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Transactional
    public List<Category> getAllActive() {
        return categoryRepo.findByIsDeletedFalse();
    }

    @Transactional
    public Category create(Category category) {
        category.setDeleted(false);
        return categoryRepo.save(category);
    }
    @Transactional
    public boolean delete(UUID id) {
        Optional<Category> opt = categoryRepo.findById(id);
        if (opt.isEmpty()) return false;

        Category cat = opt.get();
        cat.setDeleted(true);
        categoryRepo.save(cat);
        return true;
    }
}
