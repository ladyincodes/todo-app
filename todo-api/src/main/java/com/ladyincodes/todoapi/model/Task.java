package com.ladyincodes.todoapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table (name = "tasks")
@Data // generates getters, setters, toString, etc.
@NoArgsConstructor // constructor with no argument
@AllArgsConstructor // constructor with all the arguments
public class Task {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    @NonNull
    private String title;

    private String description;

    private boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    private User user;
}
