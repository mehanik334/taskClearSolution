package com.task.denisenko.taskClearSolution.rest;

import com.task.denisenko.taskClearSolution.model.User;
import com.task.denisenko.taskClearSolution.service.UserService;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {

    private UserService userService;

    @Value("${age}")
    private Integer age;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users/save")
    public ResponseEntity<User> saveUser(@ModelAttribute("user") User user) {
        if (calculateAge(user.getBirthday(), LocalDate.now()) > age && new EmailValidator().isValid(user.getEmail(), null)) {
            return new ResponseEntity<User>(userService.save(user), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/users/edit")
    public ResponseEntity<User> editUser(@ModelAttribute("user") User user) {
        return new ResponseEntity<>(userService.edit(user), HttpStatus.OK);
    }

    @PutMapping("/users/replace/{id}")
    public ResponseEntity<User> replaceUser(@PathVariable(value = "id") Integer id, @ModelAttribute("user") User user) {
        return new ResponseEntity<User>(userService.replace(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable(value = "id") Integer id) {
        userService.delete(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("users/findByBirthday")
    public ResponseEntity<List<User>> findByBirthdayBetween(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        List<User> findUsers = new ArrayList<>();
        userService.findByBirthdayBetween(from, to).forEach(findUsers::add);
        if (findUsers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(findUsers, HttpStatus.OK);
    }

    private int calculateAge(LocalDate date, LocalDate current) {
        return Period.between(date, current).getYears();
    }
}
