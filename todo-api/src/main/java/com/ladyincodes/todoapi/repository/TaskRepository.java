package com.ladyincodes.todoapi.repository;

import com.ladyincodes.todoapi.model.Task;
import com.ladyincodes.todoapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // get all the tasks for a specific user
    List<Task> findByUser(User user);

    // get one task by id, but only if it belongs to the given user
    Optional<Task> findByIdAndUser (Long id, User user);

    /***  to allow user to filter tasks using optional query parameters ***/
    // filter by UserId
    List<Task> findByUserId (Long userId);
    // filter by completion status
    List<Task> findByUserIdAndCompleted (Long userId, boolean completed);

    // filter by dueDate (ex. today)
    List<Task> findByUserIdAndDueDate(Long userId, LocalDate dueDate);

}
