package com.task.denisenko.taskClearSolution;

import com.task.denisenko.taskClearSolution.model.User;
import com.task.denisenko.taskClearSolution.repository.UserRepository;
import com.task.denisenko.taskClearSolution.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = User.builder()
                .id(1)
                .address("NewYork")
                .birthday(LocalDate.now())
                .firstName("Dima")
                .lastName("Denisenko")
                .email("we@we.ua")
                .phoneNumber("0631342222")
                .build();
    }

    @Test
    public void saveUserTest() {
        given(userRepository.findById(user.getId()))
                .willReturn(Optional.empty());

        given(userRepository.save(user)).willReturn(user);

        org.junit.jupiter.api.Assertions.assertThrows(ConfigDataResourceNotFoundException.class, () -> {
            userService.save(user);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void editUserTest() {
        given(userRepository.save(user)).willReturn(user);
        user.setEmail("sasha@gmail.com");
        user.setFirstName("Sasha");
        User updatedUser = userService.edit(user);

        assertThat(updatedUser.getEmail()).isEqualTo("sasha@gmail.com");
        assertThat(updatedUser.getFirstName()).isEqualTo("Sasha");
    }

    @Test
    public void replaceUserTest() {
        given(userRepository.save(user)).willReturn(user);

        user.setEmail("sasha@gmail.com");
        user.setFirstName("Sasha");
        User replaceUser = userService.replace(user.getId(), user);

        assertThat(replaceUser.getEmail()).isEqualTo("sasha@gmail.com");
        assertThat(replaceUser.getFirstName()).isEqualTo("Sasha");
    }

    @Test
    public void deleteUserTest() {
        Integer userId = 1;
        willDoNothing().given(userRepository).deleteById(userId);
        userService.delete(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void findByBirthdayBetweenTest() {
        User user1 = User.builder()
                .id(2)
                .address("Tokio")
                .birthday(LocalDate.now())
                .firstName("Sasha")
                .lastName("Denisov")
                .email("DS@we.ua")
                .phoneNumber("0632222222")
                .build();

        given(userRepository.findByBirthdayBetween(user.getBirthday(), user1.getBirthday())).willReturn(List.of(user, user1));

        List<User> userList = userService.findByBirthdayBetween(user.getBirthday(), user1.getBirthday());

        // then - verify the output
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(2);
    }

}
