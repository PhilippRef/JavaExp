package org.example;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LinkedPurchaseList")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LinkedPurchaseList {
    @EmbeddedId
    private LinkedPurchaseListKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Students students;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Courses courses;

}
