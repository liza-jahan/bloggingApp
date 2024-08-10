package com.example.bloggingapp.repository;

import com.example.bloggingapp.entites.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {
}
