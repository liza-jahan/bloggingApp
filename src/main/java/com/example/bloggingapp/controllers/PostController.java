package com.example.bloggingapp.controllers;

import com.example.bloggingapp.payloads.ApiResponse;
import com.example.bloggingapp.payloads.PostDto;
import com.example.bloggingapp.payloads.PostResponse;
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
    private final PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
        PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
    }

    //get by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
        List<PostDto> postDtos = this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);

    }

    //get by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDto> postDtos = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);

    }

    //get All posts
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postDto = this.postService.getAllPost();
        return new ResponseEntity<List<PostDto>>(postDto, HttpStatus.OK);
    }

    //get post details by id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("/deletePost/{postId}")
    public ApiResponse deleteByPost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ApiResponse("Post is deleted", true);

    }

    //update post
    @GetMapping("/updatePost/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId, @RequestBody PostDto postDto) {
        PostDto updatePost = this.postService.upadtePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
    }

    //pagination
    @GetMapping("/pageAble")
    public ResponseEntity<PostResponse> pageAble(
            @RequestParam(value = "pageNumber", defaultValue = "10", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false)String sortDir) {
        PostResponse postResponse = this.postService.pagination(pageNumber, pageSize, sortBy,sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }
}