package org.example;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseListKey implements Serializable {
    static final int serialVersionUID = 2;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "course_name")
    private String courseName;

}

