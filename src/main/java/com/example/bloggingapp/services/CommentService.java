package com.example.bloggingapp.services;

import com.example.bloggingapp.entites.Comment;
import com.example.bloggingapp.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId);
    void deleteComment(Integer commentId);
}
