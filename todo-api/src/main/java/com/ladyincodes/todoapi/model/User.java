package com.ladyincodes.todoapi.model;

import jakarta.persistence.*;
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
    @NonNull
    private String username;

    @Column (nullable = false)
    @NonNull
    private String password;

    @Column (nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;

}
