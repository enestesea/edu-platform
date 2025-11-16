package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", unique = true)
    @JsonIgnore
    @NotNull
    private Module courseModule;

    @Column
    private String title;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<QuizSubmission> quizSubmissions = new ArrayList<>();
}