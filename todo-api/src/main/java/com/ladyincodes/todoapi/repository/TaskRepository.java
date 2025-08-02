package com.ladyincodes.todoapi.repository;

import com.ladyincodes.todoapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findUserById(Long userId);
}
