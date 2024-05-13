package org.example.controllers;

import org.example.dto.NewsDto;
import org.example.repositories.NewsRepository;
import org.example.services.NewsCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class NewsController {

    private final NewsRepository newsRepository;
    private final NewsCRUDService newsService;

    public NewsController(NewsRepository newsRepository, NewsCRUDService newsService) {
        this.newsRepository = newsRepository;
        this.newsService = newsService;
    }

    @GetMapping("/api/news/{id}")
    public ResponseEntity getNewsById(@PathVariable Long id) {
        if (newsService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("message: " + "Новость с id " + id + " не найдена.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(newsService.getById(id));
    }

    @GetMapping("/api/news")
    public ResponseEntity<Collection<NewsDto>> getAllNews() {
        return ResponseEntity.status(HttpStatus.OK).body(newsService.getALl());
    }

    @PostMapping(value = "/api/news", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<NewsDto> createNews(@RequestBody NewsDto newsDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.create(newsDto));
    }

    @PutMapping("/api/news")
    public ResponseEntity updateNews(@RequestBody NewsDto newsDto) {
        return ResponseEntity.status(HttpStatus.OK).body(newsService.update(newsDto));
    }

    @DeleteMapping("/api/news/{id}")
    public ResponseEntity deleteNews(@PathVariable("id") Long id) {
        if (newsService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("message: " + "Новость с id " + id + " не найдена.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(newsService.delete(id));
    }
}
