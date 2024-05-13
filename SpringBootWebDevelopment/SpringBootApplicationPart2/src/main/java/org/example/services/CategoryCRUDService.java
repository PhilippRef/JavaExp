package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.CategoryDto;
import org.example.entity.Category;
import org.example.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class CategoryCRUDService implements CRUDService<CategoryDto> {

    private final CategoryRepository categoryRepository;

    public CategoryCRUDService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto getById(Long id) {
        log.info("Get by ID: " + id);
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            return null;
        }
        return mapToDto(categoryRepository.findById(id).orElseThrow());
    }

    @Override
    public Collection<CategoryDto> getALl() {
        log.info("Get all");
        return categoryRepository.findAll()
                .stream()
                .map(CategoryCRUDService::mapToDto)
                .toList();
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        log.info("Create");
        Category category = mapToEntity(categoryDto);
        Category createdCategory = categoryRepository.save(category);
        return mapToDto(createdCategory);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        log.info("Update");
        Category category = mapToEntity(categoryDto);
        Category updatedCategory = categoryRepository.save(category);
        return mapToDto(updatedCategory);
    }

    @Override
    public String delete(Long id) {
        log.info("Delete");
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            return "message: " + "Категория с id " + id + " не найдена.";
        }
        categoryRepository.deleteById(id);
        return null;
    }

    public static Category mapToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setTitle(categoryDto.getTitle());
        category.setId(categoryDto.getId());
        category.setNews(categoryDto.getNews()
                .stream()
                .map(NewsCRUDService::mapToEntity)
                .toList());
        return category;
    }

    public static CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        categoryDto.setNews(category.getNews()
                .stream()
                .map(NewsCRUDService::mapToDto)
                .toList());
        return categoryDto;
    }
}

