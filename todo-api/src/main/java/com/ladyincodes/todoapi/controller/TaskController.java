package com.ladyincodes.todoapi.controller;

import com.ladyincodes.todoapi.exception.TaskNotFoundException;
import com.ladyincodes.todoapi.model.Task;
import com.ladyincodes.todoapi.model.User;
import com.ladyincodes.todoapi.payload.request.TaskRequest;
import com.ladyincodes.todoapi.payload.response.TaskResponse;
import com.ladyincodes.todoapi.repository.TaskRepository;
import com.ladyincodes.todoapi.repository.UserRepository;
import com.ladyincodes.todoapi.security.JwtService;
import com.ladyincodes.todoapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(@RequestParam(required = false) Boolean completed, @RequestParam(required = false, defaultValue = "false") Boolean dueToday) {

        // fetch the user entity
        User user = userService.getCurrentUser();
        LocalDate today = LocalDate.now();

        // getting tasks based on params
        List<Task> tasks;
        if (completed != null) {
            tasks = taskRepository.findByUserIdAndCompleted(user.getId(), completed);
        } else if (dueToday) {
            tasks = taskRepository.findByUserIdAndDueDate(user.getId(), today);
        } else {
            tasks = taskRepository.findByUserId(user.getId());
        }

        // map each task to the TaskResponse dto
        List<TaskResponse> responses = tasks.stream().map(task -> TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .createdAt(task.getCreatedAt())
                .dueDate(task.getDueDate())
                .build()
        ).toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {

        // find the user entity
        User user = userService.getCurrentUser();

        // create and saves the task
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.isCompleted())
                .dueDate(request.getDueDate())
                .createdAt(java.time.LocalDate.now())
                .user(user)
                .build();

        Task savedTask = taskRepository.save(task);

        // return response
        TaskResponse response = TaskResponse.builder()
                .id(savedTask.getId())
                .title(savedTask.getTitle())
                .description(savedTask.getDescription())
                .completed(savedTask.isCompleted())
                .createdAt(savedTask.getCreatedAt())
                .dueDate(savedTask.getDueDate())
                .build();

        return ResponseEntity.ok(response);

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

        User user = userService.getCurrentUser();

        Task task = taskRepository.findByIdAndUser(id, user).orElseThrow(() -> new TaskNotFoundException("No task found with id: " + id));

        // update the task
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(request.isCompleted());
        task.setDueDate(request.getDueDate());

        // save the task
        Task updatedTask = taskRepository.save(task);

        // return the updated task as a response
        TaskResponse response = TaskResponse.builder()
                .id(updatedTask.getId())
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .completed(updatedTask.isCompleted())
                .createdAt(updatedTask.getCreatedAt())
                .dueDate(updatedTask.getDueDate())
                .build();

        return ResponseEntity.ok(response);

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
