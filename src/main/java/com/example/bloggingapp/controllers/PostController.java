package com.example.bloggingapp.controllers;

import com.example.bloggingapp.entites.Post;
import com.example.bloggingapp.payloads.PostDto;
import com.example.bloggingapp.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    //get by user
  @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
       List<PostDto> postDtos= this.postService.getPostsByUser(userId);
       return new ResponseEntity<List<PostDto>> (postDtos,HttpStatus.OK);

    }
    //get by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        List<PostDto> postDtos= this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>> (postDtos,HttpStatus.OK);

    }
}
