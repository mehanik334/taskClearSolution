package com.task.denisenko.taskClearSolution.service;

import com.task.denisenko.taskClearSolution.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public interface UserService {
    User save(User user);

    User edit(User user);

    User replace(Integer id, User user);

    void delete(Integer id);

    List<User> findByBirthdayBetween(LocalDate from, LocalDate to);
}
