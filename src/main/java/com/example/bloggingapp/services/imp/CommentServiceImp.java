package com.example.bloggingapp.services.imp;


import com.example.bloggingapp.entites.Comment;
import com.example.bloggingapp.entites.Post;
import com.example.bloggingapp.exception.ResourceNotFoundException;
import com.example.bloggingapp.payloads.CommentDto;
import com.example.bloggingapp.repository.CommentRepo;
import com.example.bloggingapp.repository.PostRepo;
import com.example.bloggingapp.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImp implements CommentService {
    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
        Comment comment1 = this.modelMapper.map(commentDto, Comment.class);
        comment1.setPost(post);
        Comment saveComment = this.commentRepo.save(comment1);
        return this.modelMapper.map(saveComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {

    }
}
