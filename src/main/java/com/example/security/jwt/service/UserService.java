package com.example.security.jwt.service;

import com.example.security.jwt.dto.SignUpDto;
import com.example.security.jwt.dto.UserDto;
import com.example.security.jwt.entity.User;
import com.example.security.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new BadCredentialsException("User with email: " + username + " not found"));
    }

    public UserDto signUp(SignUpDto signUpDto) {
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
        if (user.isPresent()) {
            throw new BadCredentialsException("User with email: " + signUpDto.getEmail() + " already exists");
        }
        User userToBeCreated = modelMapper.map(signUpDto, User.class);
        User savedUser = userRepository.save(userToBeCreated);
        savedUser.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        return modelMapper.map(savedUser, UserDto.class);
    }
}
