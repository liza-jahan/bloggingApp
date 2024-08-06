package com.example.bloggingapp.controllers;

import com.example.bloggingapp.entites.Post;
import com.example.bloggingapp.payloads.PostDto;
import com.example.bloggingapp.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class PostController {
    private  final PostService postService;
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody  PostDto postDto, @PathVariable Integer userId,
            @PathVariable Integer categoryId){
        PostDto createPost=this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
    }
}
