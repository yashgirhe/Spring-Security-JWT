package com.example.security.jwt.service;

import com.example.security.jwt.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPosts();

    PostDto createNewPost(PostDto postDTO);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto inputPost, Long postId);

    void deletePost(Long id);
}
