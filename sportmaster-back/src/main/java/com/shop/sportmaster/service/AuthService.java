package com.shop.sportmaster.service;

import com.shop.sportmaster.dto.LoginRequest;
import com.shop.sportmaster.dto.RegisterRequest;
import com.shop.sportmaster.model.Profile;
import com.shop.sportmaster.model.Role;
import com.shop.sportmaster.model.User;
import com.shop.sportmaster.repository.RoleRepository;
import com.shop.sportmaster.repository.UserRepository;
import com.shop.sportmaster.security.JwtService;
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
        Role role = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(new Role("USER")));

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
