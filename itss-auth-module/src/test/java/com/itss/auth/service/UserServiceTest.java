package com.itss.auth.service;

import com.itss.auth.dto.request.UserRegistrationRequest;
import com.itss.auth.repository.UserRepository;
import com.itss.auth.repository.RoleRepository;
import com.itss.auth.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private com.itss.auth.service.impl.UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserThrowsIfUsernameExists() {
        UserRegistrationRequest req = new UserRegistrationRequest();
        req.setUsername("test");
        req.setEmail("test@example.com");
        req.setPassword("password");
        when(userRepository.existsByUsername("test")).thenReturn(true);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.registerUser(req));
        assertEquals("Username already exists", ex.getMessage());
    }

    @Test
    void testRegisterUserThrowsIfEmailExists() {
        UserRegistrationRequest req = new UserRegistrationRequest();
        req.setUsername("test");
        req.setEmail("test@example.com");
        req.setPassword("password");
        when(userRepository.existsByUsername("test")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.registerUser(req));
        assertEquals("Email already exists", ex.getMessage());
    }
} 