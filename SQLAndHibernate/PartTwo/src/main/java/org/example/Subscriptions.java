package org.example;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Subscriptions")
@Data
public class Subscriptions {

    @EmbeddedId
    private SubscriptionKey id;

    @Column(name = "student_id", insertable = false, updatable = false)
    private int studentId;

    @Column(name = "course_id", insertable = false, updatable = false)
    private int courseId;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

}