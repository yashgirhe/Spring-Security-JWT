package com.example.security.jwt.controller;

import com.example.security.jwt.advise.ApiError;
import com.example.security.jwt.dto.LoginDto;
import com.example.security.jwt.dto.SignUpDto;
import com.example.security.jwt.dto.UserDto;
import com.example.security.jwt.service.AuthService;
import com.example.security.jwt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Authentication APIs", description = "Signup and Login APIs")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Operation(
            summary = "Sign Up"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid input",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "401", description = "Conflict: User with the given email already exists",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))})})
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Authenticate user and return JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully",
                    content = {@Content(mediaType = "text/html",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid input",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "401", description = "Not Found: User with the given email not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))})})
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        return ResponseEntity.ok(token);
    }
}
