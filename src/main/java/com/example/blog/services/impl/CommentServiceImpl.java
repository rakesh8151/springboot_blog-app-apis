package com.example.blog.services.impl;

import com.example.blog.entities.Comment;
import com.example.blog.entities.Post;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.CommentDto;
import com.example.blog.repositories.CommentRepository;
import com.example.blog.repositories.PostRepository;
import com.example.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto,Integer postId) {
        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","post id",postId));
        Comment comment=modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        Comment comment1=commentRepository.save(comment);
        return modelMapper.map(comment1,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
      Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","comment id",commentId));
      commentRepository.delete(comment);
    }
}
