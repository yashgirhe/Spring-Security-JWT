package com.example.security.jwt.controller;

import com.example.security.jwt.advise.ApiError;
import com.example.security.jwt.dto.PostDto;
import com.example.security.jwt.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/posts")
@Tag(name = "Posts APIs")
public class PostController {

    private final PostService postService;

    @Operation(
            summary = "Get all posts",
            description = "User and Admin both can access this API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all posts.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "User do not have permission to access this resource.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated. They need to log in to access this resource.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"You need to log in to access this resource.\"}"
                            )
                    )
            )
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @Operation(
            summary = "Get post by id",
            description = "User and Admin both can access this API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "User do not have permission to access this resource.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Post not found with given id.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated. They need to log in to access this resource.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"You need to log in to access this resource.\"}"
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public PostDto getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @Operation(
            summary = "Create new post",
            description = "Only Admin can access this API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created post.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "User do not have permission to access this resource.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated. They need to log in to access this resource.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"You need to log in to access this resource.\"}"
                            )
                    )
            )
    })
    @PostMapping
    @Secured("ROLE_ADMIN")
    public PostDto createNewPost(@RequestBody PostDto inputPost) {
        return postService.createNewPost(inputPost);
    }

    @Operation(
            summary = "Update post",
            description = "Only Admin can access this API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created post.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "User do not have permission to access this resource.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Post not found with given id.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated. They need to log in to access this resource.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"You need to log in to access this resource.\"}"
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public PostDto updatePost(@RequestBody PostDto inputPost, @PathVariable Long id) {
        return postService.updatePost(inputPost, id);
    }

    @Operation(
            summary = "Delete post",
            description = "Only Admin can access this API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created post.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "User do not have permission to access this resource.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Post not found with given id.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated. They need to log in to access this resource.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"You need to log in to access this resource.\"}"
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
