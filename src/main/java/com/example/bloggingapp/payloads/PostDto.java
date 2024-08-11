package com.example.bloggingapp.payloads;

import com.example.bloggingapp.entites.Category;
import com.example.bloggingapp.entites.Comment;
import com.example.bloggingapp.entites.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Date addDate;
    private Category category;
    private User user;

    private Set<CommentDto>comments=new HashSet<>();
}
