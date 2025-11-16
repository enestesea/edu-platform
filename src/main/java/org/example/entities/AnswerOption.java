package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "answer_options")
@Getter
@Setter
public class AnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected UUID id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnore
    @NotNull
    private Question question;

    @Column(nullable = false)
    @NotBlank
    private String text;

    @Column(nullable = false, name = "is_correct")
    @NotNull
    private boolean isCorrect;
}