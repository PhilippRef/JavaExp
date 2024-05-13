package main.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "task")
@NoArgsConstructor
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_seq", allocationSize = 1)
    private int id;

    private Date creationTime;

    private boolean isDone;

    private String title;

    private String description;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
