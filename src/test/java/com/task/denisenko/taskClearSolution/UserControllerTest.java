package com.task.denisenko.taskClearSolution;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.denisenko.taskClearSolution.model.User;
import com.task.denisenko.taskClearSolution.repository.UserRepository;
import com.task.denisenko.taskClearSolution.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest

public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;


    @Test
    public void saveUserTest() throws Exception {
        User user = User.builder()
                .id(1)
                .address("NewYork")
                .birthday(LocalDate.now())
                .firstName("Dima")
                .lastName("Denisenko")
                .email("we@we.ua")
                .phoneNumber("0631342222")
                .build();
        given(userService.save(any(User.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/users/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(user.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(user.getEmail())));
    }

    @Test
    public void deleteUserTestSuccess() throws Exception {
        User user = User.builder()
                .id(1)
                .address("NewYork")
                .birthday(LocalDate.now())
                .firstName("Dima")
                .lastName("Denisenko")
                .email("we@we.ua")
                .phoneNumber("0631342222")
                .build();
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
