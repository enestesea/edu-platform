package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "course_reviews")
@Getter
@Setter
public class CourseReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    @NotNull
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore
    @NotNull
    private User student;

    @Column(nullable = false)
    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Integer rating;

    @Column
    private String comment;

    @Column
    private Instant createdAtReview = Instant.now();
}