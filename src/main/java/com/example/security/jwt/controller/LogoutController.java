package com.example.security.jwt.controller;

import com.example.security.jwt.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/logout/")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Logout")
public class LogoutController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        //blacklist access token
        authService.logout(request, response);
        return ResponseEntity.ok("Logged out successfully");
    }

}
