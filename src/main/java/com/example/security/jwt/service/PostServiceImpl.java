package com.example.security.jwt.service;

import com.example.security.jwt.dto.PostDto;
import com.example.security.jwt.entity.Post;
import com.example.security.jwt.exceptions.ResourceNotFoundException;
import com.example.security.jwt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(Post -> modelMapper.map(Post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto createNewPost(PostDto inputPost) {
        Post Post = modelMapper.map(inputPost, Post.class);
        return modelMapper.map(postRepository.save(Post),PostDto.class);
    }

    @Override
    public PostDto getPostById(Long id) {
        Post Post = postRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found"));
        return modelMapper.map(Post,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto inputPost, Long postId) {
        Post existingPost = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found"));
        inputPost.setId(postId);
        modelMapper.map(inputPost, existingPost);
        Post savedPost = postRepository.save(existingPost);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
