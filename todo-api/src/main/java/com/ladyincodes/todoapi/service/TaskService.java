package com.ladyincodes.todoapi.service;

import com.ladyincodes.todoapi.exception.TaskNotFoundException;
import com.ladyincodes.todoapi.model.Task;
import com.ladyincodes.todoapi.model.User;
import com.ladyincodes.todoapi.payload.request.TaskRequest;
import com.ladyincodes.todoapi.payload.response.TaskResponse;
import com.ladyincodes.todoapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public void deleteTaskById(Long id) {
        User user = userService.getCurrentUser();

        Task task = taskRepository.findByIdAndUser(id, user).orElseThrow(() -> new TaskNotFoundException("No task found with id: " + id));

        // delete the task
        taskRepository.delete(task);
    }

    public TaskResponse updateTask(Long id, TaskRequest request) {
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
        return TaskResponse.builder()
                .id(updatedTask.getId())
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .completed(updatedTask.isCompleted())
                .createdAt(updatedTask.getCreatedAt())
                .dueDate(updatedTask.getDueDate())
                .build();
    }

    public TaskResponse createTask (TaskRequest request) {
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
        return TaskResponse.builder()
                .id(savedTask.getId())
                .title(savedTask.getTitle())
                .description(savedTask.getDescription())
                .completed(savedTask.isCompleted())
                .createdAt(savedTask.getCreatedAt())
                .dueDate(savedTask.getDueDate())
                .build();
    }

    public List<TaskResponse> getFilteredTasks(Boolean completed, Boolean dueToday) {
        User currentUser = userService.getCurrentUser();
        Long userId = currentUser.getId();
        LocalDate today = LocalDate.now();

        // getting tasks based on params
        List<Task> tasks;
        if (completed != null) {
            tasks = taskRepository.findByUserIdAndCompleted(userId, completed);
        } else if (dueToday) {
            tasks = taskRepository.findByUserIdAndDueDate(userId, today);
        } else {
            tasks = taskRepository.findByUserId(userId);
        }

        return tasks.stream().map(this::mapToResponse).toList();

    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .createdAt(task.getCreatedAt())
                .dueDate(task.getDueDate())
                .build();
    }
}
