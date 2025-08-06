package com.ladyincodes.todoapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Entity
@Table(name = "users")
@Data // generates getters, setters, toString, etc.
@NoArgsConstructor // constructor with no argument
@AllArgsConstructor // constructor with all the arguments
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, unique = true)
    @NotBlank (message = "Username is required")
    private String username;

    @Column (nullable = false)
    @NotBlank(message = "Password is required")
    private String password;

    @Email (message = "Email should be valid")
    @Column (nullable = false, unique = true)
    @NotBlank (message = "Email is required")
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

}
