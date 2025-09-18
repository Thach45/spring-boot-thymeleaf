package com.example.spring_thymeleaf.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.spring_thymeleaf.demo.model.Category;
import com.example.spring_thymeleaf.demo.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String list(Model model,
                      @RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "5") int size,
                      @RequestParam(required = false) String keyword) {
        
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("name"));
        Page<Category> categoryPage = categoryService.search(keyword, pageRequest);
        
        model.addAttribute("categories", categoryPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        
        return "category/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "category/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Category category) {
        category.setId(id);
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryService.softDelete(id);
        return "redirect:/categories";
    }
}
