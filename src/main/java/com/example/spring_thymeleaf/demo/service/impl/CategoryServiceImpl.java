package com.example.spring_thymeleaf.demo.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring_thymeleaf.demo.model.Category;
import com.example.spring_thymeleaf.demo.repository.CategoryRepository;
import com.example.spring_thymeleaf.demo.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findByActiveTrue(pageable);
    }

    @Override
    public Page<Category> search(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll(pageable);
        }
        return categoryRepository.searchCategories(keyword.trim(), pageable);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void softDelete(Long id) {
        Category category = findById(id);
        category.setActive(false);
        categoryRepository.save(category);
    }
}
