package com.ladyincodes.todoapi.controller;

import com.ladyincodes.todoapi.exception.TaskNotFoundException;
import com.ladyincodes.todoapi.model.Task;
import com.ladyincodes.todoapi.model.User;
import com.ladyincodes.todoapi.payload.request.TaskRequest;
import com.ladyincodes.todoapi.payload.response.TaskResponse;
import com.ladyincodes.todoapi.repository.TaskRepository;
import com.ladyincodes.todoapi.repository.UserRepository;
import com.ladyincodes.todoapi.security.JwtService;
import com.ladyincodes.todoapi.service.TaskService;
import com.ladyincodes.todoapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final TaskService taskService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(@RequestParam(required = false) Boolean completed, @RequestParam(required = false, defaultValue = "false") Boolean dueToday) {

        // getting tasks based on params
        return ResponseEntity.ok(taskService.getFilteredTasks(completed, dueToday));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        // Save the task
        TaskResponse response = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {

        // fetch the user
        User user = userService.getCurrentUser();

        // fetch the task by id and user
        Task task = taskRepository.findByIdAndUser(id, user).orElseThrow(() -> new TaskNotFoundException("No task found with id: " + id));

        // convert to TaskResponse dto
        TaskResponse response = TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .createdAt(task.getCreatedAt())
                .dueDate(task.getDueDate())
                .build();

        return ResponseEntity.ok(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask (@PathVariable Long id,
                                                    @Valid @RequestBody TaskRequest request) {

        TaskResponse updatedTask = taskService.updateTask(id, request);
        return ResponseEntity.ok(updatedTask);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        User user = userService.getCurrentUser();

        Task task = taskRepository.findByIdAndUser(id, user).orElseThrow(() -> new TaskNotFoundException("No task found with id: " + id));

        // delete the task
        taskRepository.delete(task);

        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
