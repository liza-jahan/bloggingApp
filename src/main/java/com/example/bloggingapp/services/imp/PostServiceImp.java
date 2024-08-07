package com.example.bloggingapp.services.imp;

import com.example.bloggingapp.entites.Category;
import com.example.bloggingapp.entites.Post;
import com.example.bloggingapp.entites.User;
import com.example.bloggingapp.exception.ResourceNotFoundException;
import com.example.bloggingapp.payloads.PostDto;
import com.example.bloggingapp.payloads.PostResponse;
import com.example.bloggingapp.repository.CategoryRepo;
import com.example.bloggingapp.repository.PostRepo;
import com.example.bloggingapp.repository.UserRepo;
import com.example.bloggingapp.services.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImp implements PostService {
    private final PostRepo postRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepo.save(post);


        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto upadtePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setAddDate(new Date());
        Post updatePost = this.postRepo.save(post);
        return this.modelMapper.map(updatePost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
        this.postRepo.delete(post);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts = this.postRepo.findAll();
        List<PostDto> postDto = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDto;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
        List<Post> post = this.postRepo.findByCategory(category);
        List<PostDto> postDto = post.stream().map(post1 -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDto;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDto = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDto;
    }

    @Override
    public List<Post> searchPost(String keyWord) {
        return null;
    }

    @Override
    public PostResponse pagination(Integer pageNumber, Integer pageSize) {

        Pageable p = PageRequest.of(pageNumber, pageSize);
        Page<Post> postPage = this.postRepo.findAll(p);
        List<Post> posts = postPage.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getNumberOfElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
    }
}
