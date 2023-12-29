package com.example.blog.repositories;

import com.example.blog.entities.Category;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    Page<Post> findByUser(User user,Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);

    List<Post> findByTitleContaining(String title);
}
