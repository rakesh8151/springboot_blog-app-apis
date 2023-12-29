package com.example.blog.services.impl;

import com.example.blog.entities.Category;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.PostDto;
import com.example.blog.payloads.PostResponse;
import com.example.blog.repositories.CategoryRepository;
import com.example.blog.repositories.PostRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class PostServiceImpl  implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto,Integer categoryId,Integer userId) {
        User user =userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","userId",userId));
        Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryId));
       Post post=modelMapper.map(postDto, Post.class);
       post.setImageName("default.png");
       post.setAddedDate(new Date());
       post.setCategory(category);
       post.setUser(user);
      Post savedPost =postRepository.save(post);
        return modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","postId",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post savedpost=postRepository.save(post);
        PostDto postDto1=modelMapper.map(savedpost, PostDto.class);
        return   postDto1;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post =postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","postId",postId));

        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable p= PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> postPage=postRepository.findAll(p);
        List<Post> posts=postPage.getContent();
        List<PostDto> postDtos=posts.stream().map((post ->  modelMapper.map(post, PostDto.class))).toList();
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getNumberOfElements());
        postResponse.setLastPage(postPage.isLast());
        postResponse.setTotalPages(postPage.getTotalPages());
        return postResponse;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post =postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","postId",postId));
        postRepository.delete(post);

    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryId));
        Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable p= PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> postPage=postRepository.findByCategory(category,p);
        List<Post> posts=postPage.getContent();

        List<PostDto> postDtos=posts.stream().map((post ->  modelMapper.map(post, PostDto.class))).toList();
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getNumberOfElements());
        postResponse.setLastPage(postPage.isLast());
        postResponse.setTotalPages(postPage.getTotalPages());
        return postResponse;
    }

    @Override
    public PostResponse getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
        User user =userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","userId",userId));
        Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable p= PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> postPage=postRepository.findByUser(user,p);
        List<Post> posts=postPage.getContent();
        List<PostDto> postDtos=posts.stream().map((post ->  modelMapper.map(post, PostDto.class))).toList();
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getNumberOfElements());
        postResponse.setLastPage(postPage.isLast());
        postResponse.setTotalPages(postPage.getTotalPages());
        return postResponse;
    }


    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts=postRepository.findByTitleContaining(keyword);
        List<PostDto> postDtos=posts.stream().map((post ->  modelMapper.map(post, PostDto.class))).toList();
        return postDtos;
    }
}
