package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "submissions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"assignment_id","student_id"}))
@Getter
@Setter
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    @JsonIgnore
    @NotNull
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore
    @NotNull
    private User student;

    @Column
    private Instant submittedAt = Instant.now();

    @Column
    private String content;

    @PositiveOrZero
    private Integer score;

    @Column
    private String feedback;
}