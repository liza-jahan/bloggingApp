package com.example.bloggingapp.services;

import com.example.bloggingapp.entites.Post;
import com.example.bloggingapp.payloads.PostDto;

import java.util.List;

public interface PostService {
   //create
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    //Update
    Post upadtePost(PostDto postDto,Integer postId);
    void deletePost(Integer postId);
    //get all post
    List<Post> getAllPost();
    //get single post
    Post getPostById(Integer postId);
    //get All post by category
    List<Post> getPostsByCategory(Integer categoryId);
    //get All post by
    List<Post> getPostsByUser(Integer userId);
    //search Posts
    List<Post> searchPost(String keyWord);
}
