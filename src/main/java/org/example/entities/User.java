package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected UUID id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotBlank
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private UserRole role;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @JsonIgnore
    private Profile profile;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Submission> submissions = new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<QuizSubmission> quizSubmissions = new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CourseReview> courseReviews = new ArrayList<>();
    
}