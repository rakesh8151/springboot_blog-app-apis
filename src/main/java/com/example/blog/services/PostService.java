package com.example.blog.services;

import com.example.blog.payloads.PostDto;
import com.example.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Integer categoryId,Integer userId);

    PostDto updatePost(PostDto postDto,Integer postId);

    PostDto getPostById(Integer postId);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    void deletePost(Integer postId);

    PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    PostResponse getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    List<PostDto> searchPosts(String keyword);
}
