package com.ladyincodes.todoapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ladyincodes.todoapi.model.Task;
import com.ladyincodes.todoapi.model.User;
import com.ladyincodes.todoapi.repository.TaskRepository;
import com.ladyincodes.todoapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @WithMockUser(username = "user@example.com")
    void shouldCreateTaskSuccessfully () throws Exception {
        if (userRepository.findByEmail("user@example.com").isEmpty()) {
            User newUser = new User();
            newUser.setEmail("user@example.com");
            newUser.setUsername("Mock User");
            newUser.setPassword("encodedpassword");
            userRepository.save(newUser);
        }

        // json request
        var request = new TaskRequest("Test task", "Test description", false, LocalDate.now());

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test task"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    @WithMockUser (username = "user@example.com")
    void shouldReturnBadRequestWhenTitleMissing() throws Exception {
        var invalidRequest = new TaskRequest(
                "", // title missing
                "Description",
                false,
                LocalDate.now()
        );

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists()); // CustomErrorResponse.message



    }

    @Test
    @WithMockUser(username = "user@example.com", roles = "USER")
    void shouldReturnTaskByIdForAuthenticatedUser() throws Exception {
        // Ensure the user exists
        User user = userRepository.findByEmail("user@example.com")
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail("user@example.com");
                    newUser.setUsername("Mock User");
                    newUser.setPassword("encoded");
                    return userRepository.save(newUser);
                });

        Task task = Task.builder()
                .title("Read book")
                .description("Read Java Concurrency in Practice")
                .completed(false)
                .dueDate(LocalDate.now())
                .createdAt(LocalDate.now())
                .user(user)
                .build();

        Task savedTask = taskRepository.save(task);

        mockMvc.perform(get("/api/tasks/" + savedTask.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedTask.getId()))
                .andExpect(jsonPath("$.title").value("Read book"))
                .andExpect(jsonPath("$.completed").value(false));
    }


    record TaskRequest(String title, String description, boolean completed, LocalDate dueDate) {}

}
