package com.example.security.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@Tag(name = "Home Controller")
public class HomeController {

    @Operation(summary = "Only authenticated user can access")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved data",
                    content = {@Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "403", description = "Forbidden - User is not authorized or JWT token is missing",
                    content = {@Content(mediaType = "application/json")})})
    @GetMapping
    public String getMethod() {
        return "You are authenticated";
    }
}
