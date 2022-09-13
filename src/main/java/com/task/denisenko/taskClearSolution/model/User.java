package com.task.denisenko.taskClearSolution.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;

    @Column(name = "first_name")
    @NotEmpty
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    @Column(name = "email")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @Column(name = "birthday")
    @NotEmpty
    private LocalDate birthday;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

}
