package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.NewsDto;
import org.example.entity.Category;
import org.example.entity.News;
import org.example.repositories.CategoryRepository;
import org.example.repositories.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class NewsCRUDService implements CRUDService<NewsDto> {
    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;

    public NewsCRUDService(NewsRepository newsRepository, CategoryRepository categoryRepository) {
        this.newsRepository = newsRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public NewsDto getById(Long id) {
        log.info("Get by ID: " + id);
        Optional<News> newsOptional = newsRepository.findById(Math.toIntExact(id));
        if (newsOptional.isEmpty()) {
            return null;
        }
        return mapToDto(newsRepository.findById(Math.toIntExact(id)).orElseThrow());
    }

    @Override
    public Collection<NewsDto> getALl() {
        log.info("Get all");
        return newsRepository.findAll()
                .stream()
                .map(NewsCRUDService::mapToDto)
                .toList();
    }

    @Override
    public NewsDto create(NewsDto newsDto) {
        log.info("Create");
        News news = mapToEntity(newsDto);
        String newsDtoCategoryType = newsDto.getCategory();
        Category category = categoryRepository
                .findByCategoryTitle(newsDtoCategoryType);
        news.setCategory(category);
        News createdNews = newsRepository.save(news);
        return mapToDto(createdNews);
    }

    @Override
    public NewsDto update(NewsDto newsDto) {
        News news = mapToEntity(newsDto);
        String newsDtoCategoryType = newsDto.getCategory();
        Category category = categoryRepository
                .findByCategoryTitle(newsDtoCategoryType);
        news.setCategory(category);
        News updatedNews = newsRepository.save(news);
        return mapToDto(updatedNews);
    }

    @Override
    public String delete(Long id) {
        log.info("Delete " + id);
        Optional<News> newsOptional = newsRepository.findById(Math.toIntExact(id));
        if (newsOptional.isEmpty()) {
            return "message: " + "Новость с id " + id + " не найдена.";
        }
        newsRepository.deleteById(Math.toIntExact(id));
        return null;
    }

    public static NewsDto mapToDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setText(news.getText());
        newsDto.setCategory(news.getCategory().getTitle());
        newsDto.setDate(new Date().toInstant());
        return newsDto;
    }

    public static News mapToEntity(NewsDto newsDto) {
        News news = new News();
        news.setId(newsDto.getId());
        news.setTitle(newsDto.getTitle());
        news.setText(newsDto.getText());
        news.setDate(new Date().toInstant());
        return news;
    }
}