package com.ladyincodes.todoapi.repository;

import com.ladyincodes.todoapi.model.Task;
import com.ladyincodes.todoapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // gets all the tasks for a specific user
    List<Task> findUserById(Long userId);

    // gets one task by id, but only if it belongs to the given user
    Optional<Task> findByIdAndUser (Long id, User user);
}
