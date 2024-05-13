package org.example.controllers;

import org.example.dto.CategoryDto;
import org.example.services.CategoryCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CategoryController {
    private final CategoryCRUDService categoryService;

    public CategoryController(CategoryCRUDService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/categories/{id}")
    public ResponseEntity getCategoryById(@PathVariable Long id) {
        if (categoryService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("message: " + "Категория с id " + id + " не найдена.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getById(id));
    }

    @GetMapping("/api/categories")
    public ResponseEntity<Collection<CategoryDto>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getALl());
    }

    @PostMapping(value = "/api/categories", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(categoryDto));
    }

    @PutMapping("/api/categories")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(categoryDto));
    }

    @DeleteMapping("/api/categories/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        if (categoryService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("message: " + "Категория с id " + id + " не найдена.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.delete(id));
    }
}