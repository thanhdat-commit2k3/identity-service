package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;

    @BeforeEach
    void initData() {
        LocalDate dob = LocalDate.of(2003, 3, 9);

        request = UserCreationRequest.builder()
                .username("thanhdat")
                .firstName("thanh")
                .lastName("dat")
                .password("12345678")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id(("5354346867"))
                .username("thanhdat")
                .firstName("thanh")
                .lastName("dat")
                .dob(dob)
                .build();

        user = User.builder()
                .id("5354346867")
                .username("thanhdat")
                .firstName("thanh")
                .lastName("dat")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // Given
        Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(false);
        Mockito.when(userRepository.save(any())).thenReturn(user);

        // When
        var response = userService.createUser(request);

        // Then
        Assertions.assertThat(response.getId()).isEqualTo("5354346867");
        Assertions.assertThat(response.getUsername()).isEqualTo("thanhdat");
    }

    @Test
    void createUser_userExisted_fail() {
        // Given
        Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // When
        var exception = assertThrows(AppException.class,
                () -> userService.createUser(request));

        // Then
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }
}
