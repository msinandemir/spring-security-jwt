package com.msdemir.springsecurityjwt.services;

import com.msdemir.springsecurityjwt.dtos.requests.AddUserRequest;
import com.msdemir.springsecurityjwt.dtos.responses.AddUserResponse;
import com.msdemir.springsecurityjwt.models.Role;
import com.msdemir.springsecurityjwt.models.User;
import com.msdemir.springsecurityjwt.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AddUserResponse addUser(AddUserRequest request) {
        List<Role> authorities = new ArrayList<>();
        authorities.add(Role.ROLE_USER);

        User user = User.builder()
                .name(request.name())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .authorities(authorities)
                .build();

        User savedUser = userRepository.save(user);

        return AddUserResponse.builder()
                .id(savedUser.getId())
                .createdAt(savedUser.getCreatedAt())
                .createdAt(savedUser.getCreatedAt())
                .name(savedUser.getName())
                .username(savedUser.getUsername())
                .authorities(savedUser.getAuthorities())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }
}
