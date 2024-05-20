package com.msdemir.springsecurityjwt.services;

import com.msdemir.springsecurityjwt.dtos.requests.AddUserRequest;
import com.msdemir.springsecurityjwt.dtos.responses.AddUserResponse;
import com.msdemir.springsecurityjwt.models.Role;
import com.msdemir.springsecurityjwt.models.User;
import com.msdemir.springsecurityjwt.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService cut;

    private User user;

    private List<Role> authorities;

    private final String username = "test";

    @BeforeEach
    void setUp() {
        authorities = new ArrayList<>();
        authorities.add(Role.ROLE_USER);

        user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword("123");
        user.setName("test-name");
        user.setAuthorities(authorities);
    }

    @Test
    @DisplayName("Test addUser method should save the user and return saved user with dto")
    void should_save_the_user_and_return_saved_user_with_dto() {
        AddUserRequest addUserRequest = new AddUserRequest(
                "test-name",
                username,
                "123"
        );

        when(passwordEncoder.encode("123")).thenReturn("encoded-pass");
        when(userRepository.save(any(User.class))).thenReturn(user);

        AddUserResponse savedUser = cut.addUser(addUserRequest);

        assertNotNull(savedUser);
        assertEquals(1L, savedUser.id());
        assertEquals("test", savedUser.username());
        assertEquals("test-name", savedUser.name());
        assertEquals(authorities, savedUser.authorities());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User capuredUser = userCaptor.getValue();
        assertEquals("test", capuredUser.getUsername());
        assertEquals("test-name", capuredUser.getName());
        assertEquals("encoded-pass", capuredUser.getPassword());
        assertEquals(authorities, capuredUser.getAuthorities());
    }

    @Test
    @DisplayName("Test loadUserByUsername method should find user by username and return the user")
    void should_find_user_by_username_and_return_the_user() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails foundUser = cut.loadUserByUsername(username);

        assertNotNull(foundUser);
        assertEquals("test", foundUser.getUsername());

        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("Test loadUserByUsername method should not find the user and throw exception")
    void should_not_find_the_user_by_username_and_throw_exception() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> cut.loadUserByUsername(username));

        assertNull(null, exception.getMessage());

        verify(userRepository, times(1)).findByUsername(username);
    }
}
