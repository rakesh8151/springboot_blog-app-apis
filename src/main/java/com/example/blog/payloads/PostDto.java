package com.example.blog.payloads;

import com.example.blog.entities.Category;
import com.example.blog.entities.Comment;
import com.example.blog.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Integer postId;
    @NotEmpty
    @Size(min = 4,message = "post title must be min of 4 characters")
    private  String title;
    @NotEmpty
    @Size(min = 10,message = "post contents must be min of 10 characters")
    private  String content;
    private  String imageName;
    private Date addedDate;


    private CategoryDto category;

    private UserDto user;

    private Set<CommentDto> comments=new HashSet<>();
}
