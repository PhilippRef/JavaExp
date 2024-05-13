package org.example.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String title;
    private List<NewsDto> news = new ArrayList<>();
}
