package com.example.security.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Home Controller")
public class HomeController {

    @Operation(summary = "Any user can access this API",
            description = "No login is required")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved data",
                    content = {@Content(mediaType = "text/html")})})
    @GetMapping("/home")
    public String home() {
        return "Welcome to home page";
    }

}
