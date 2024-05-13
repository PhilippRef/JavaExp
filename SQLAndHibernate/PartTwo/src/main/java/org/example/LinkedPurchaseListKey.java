package org.example;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LinkedPurchaseListKey implements Serializable {
    static final int serialVersionUID = 1;
    @Column(name = "student_id")
    private String studentId;
    @Column(name = "course_id")
    private String courseId;

}
