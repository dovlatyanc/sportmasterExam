package com.shop.sportmaster.service;

import com.shop.sportmaster.dto.RegisterRequest;
import com.shop.sportmaster.model.Profile;
import com.shop.sportmaster.model.Role;
import com.shop.sportmaster.model.User;
import com.shop.sportmaster.repository.RoleRepository;
import com.shop.sportmaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {

        if (userRepository.findByEmail(request.email).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        User user = new User();
        user.setEmail(request.email);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setRole(role);

        Profile profile = new Profile();
        profile.setFullName(request.fullName);
        profile.setEmail(request.email);
        profile.setUser(user);

        user.setProfile(profile);

        userRepository.save(user);
    }
}
