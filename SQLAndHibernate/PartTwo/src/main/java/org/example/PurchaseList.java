package org.example;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "PurchaseList")
@Data
public class PurchaseList {
    @EmbeddedId
    private PurchaseListKey id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_name", referencedColumnName = "name", insertable = false, updatable = false)
    Students students;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_name", referencedColumnName = "name", insertable = false, updatable = false)
    Courses courses;

    private int price;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_date")
    private Date subscriptionDate;

}
