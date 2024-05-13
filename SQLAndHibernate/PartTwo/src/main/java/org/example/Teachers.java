package org.example;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Teachers")
@Data
public class Teachers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int salary;

    private int age;

    @OneToMany(mappedBy = "teachers")
    private List<Courses> courses;

}
