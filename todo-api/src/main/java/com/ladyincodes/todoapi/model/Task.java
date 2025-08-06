package com.ladyincodes.todoapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table (name = "tasks")
@Data // generates getters, setters, toString, etc.
@NoArgsConstructor // constructor with no argument
@AllArgsConstructor // constructor with all the arguments
@Builder
public class Task {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    @NotBlank (message = "Title is required")
    private String title;

    private String description;

    private boolean completed = false;

    private LocalDateTime dueDate;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
