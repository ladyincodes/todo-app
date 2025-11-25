package com.ladyincodes.todoapi.service;

import com.ladyincodes.todoapi.model.Task;
import com.ladyincodes.todoapi.model.User;
import com.ladyincodes.todoapi.payload.response.TaskResponse;
import com.ladyincodes.todoapi.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldReturnAllTasksForUserWhenNoFilterApplied() {
        User mockUser = new User();
        mockUser.setId(2L);
        when(userService.getCurrentUser()).thenReturn(mockUser);

        Task task = Task.builder()
                .id(1L)
                .title("Sample task")
                .completed(false)
                .dueDate(LocalDate.now())
                .user(mockUser)
                .build();

        when(taskRepository.findByUserId(2L)).thenReturn(List.of(task));

        List<TaskResponse> result = taskService.getFilteredTasks(null, false);

        assertEquals(1, result.size());
        assertEquals("Sample task", result.get(0).getTitle());

    }


}
