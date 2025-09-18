package com.ladyincodes.todoapi.payload.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {

    @NotBlank (message = "Title is required")
    @Size (max = 100, message = "Title must be at most 100 characters")
    private String title;

    @Size (max = 1000, message = "Description must be at most 1000 characters")
    private String description;

    private boolean completed;

    @FutureOrPresent (message = "Due date cannot be in the past")
    private LocalDate dueDate;
}
