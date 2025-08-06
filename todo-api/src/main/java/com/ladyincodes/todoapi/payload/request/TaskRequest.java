package com.ladyincodes.todoapi.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {

    @NotBlank (message = "Title is required")
    private String title;
    private String description;
    private boolean completed;
    private LocalDate dueDate;
}
