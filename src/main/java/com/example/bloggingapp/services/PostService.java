package com.example.bloggingapp.services;

import com.example.bloggingapp.entites.Post;
import com.example.bloggingapp.payloads.PostDto;

import java.util.List;

public interface PostService {
    //create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //Update
    PostDto upadtePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    //get all post
    List<PostDto> getAllPost();

    //get single post
    PostDto getPostById(Integer postId);

    //get All post by category
    List<PostDto> getPostsByCategory(Integer categoryId);

    //get All post by user
    List<PostDto> getPostsByUser(Integer userId);

    //search Posts
    List<Post> searchPost(String keyWord);

    List<PostDto> pagination(Integer pageNumber, Integer pageSize);
}
