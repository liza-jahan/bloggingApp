package com.example.bloggingapp.controllers;

import com.example.bloggingapp.config.AppConstant;
import com.example.bloggingapp.payloads.ApiResponse;
import com.example.bloggingapp.payloads.PostDto;
import com.example.bloggingapp.payloads.PostResponse;
import com.example.bloggingapp.services.FileService;
import com.example.bloggingapp.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final FileService fileService;
    @Value("${project.image}")
    private String path;

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
    public ResponseEntity<PostResponse> pageAble(@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {
        PostResponse postResponse = this.postService.pagination(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    //search
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords) {
        List<PostDto> searchResult = this.postService.searchPost(keywords);
        return new ResponseEntity<List<PostDto>>(searchResult, HttpStatus.OK);

    }

    //post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(@PathVariable Integer postId, @RequestParam("image") MultipartFile image) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updatePost = this.postService.upadtePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
    }
}