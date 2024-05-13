package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "category_type")
    private Category category;

    public News(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
