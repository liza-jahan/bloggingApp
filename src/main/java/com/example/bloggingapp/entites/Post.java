package com.example.bloggingapp.entites;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="posts")
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer post_id;
    @Column(name="post_title",length = 100,nullable = false)
    private String title;
    private String content;
    private String imageName;
    private Date addDate;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;

}
