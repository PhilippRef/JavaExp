package org.example;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Courses")
@Data
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "enum('DESIGN', 'PROGRAMMING', 'MARKETING', 'MANAGEMENT', 'BUSINESS')")
    private CourseType type;

    private String description;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teachers teachers;

    @Column(name = "students_count")
    private int studentsCount;

    private int price;

    @Column(name = "price_per_hour")
    private int pricePerHour;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Subscriptions",
    joinColumns = {@JoinColumn(name = "course_id")},
    inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private List<Students> students;

}
