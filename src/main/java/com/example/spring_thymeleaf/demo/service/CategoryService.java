package com.example.spring_thymeleaf.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.spring_thymeleaf.demo.model.Category;

public interface CategoryService {
    Category save(Category category);
    Category findById(Long id);
    Page<Category> findAll(Pageable pageable);
    Page<Category> search(String keyword, Pageable pageable);
    void deleteById(Long id);
    void softDelete(Long id);
}
