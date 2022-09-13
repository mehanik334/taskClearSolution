package com.task.denisenko.taskClearSolution.repository;

import com.task.denisenko.taskClearSolution.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User replace(Integer id, User user);
    List<User> findByBirthdayBetween(Date from, Date to);

}
