package com.ladyincodes.todoapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
