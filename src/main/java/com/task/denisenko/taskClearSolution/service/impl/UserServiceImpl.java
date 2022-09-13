package com.task.denisenko.taskClearSolution.service.impl;

import com.task.denisenko.taskClearSolution.model.User;
import com.task.denisenko.taskClearSolution.repository.UserRepository;
import com.task.denisenko.taskClearSolution.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User edit(User user) {
        return userRepository.save(user);
    }

    @Override
    public User replace(Integer id, User user) {

        return userRepository.findById(id)
                .map(user1 -> {
                    user1.setAddress(user.getAddress());
                    user1.setBirthday(user.getBirthday());
                    user1.setEmail(user.getEmail());
                    user1.setFirstName(user.getFirstName());
                    user1.setLastName(user.getLastName());
                    user1.setPhoneNumber(user.getPhoneNumber());
                    return userRepository.save(user1);
                })
                .orElseGet(() -> {
                    user.setId(id);
                    return userRepository.save(user);
                });
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findByBirthdayBetween(LocalDate from, LocalDate to) {
        return userRepository.findByBirthdayBetween(from, to);
    }
}
