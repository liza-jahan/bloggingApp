package com.example.bloggingapp.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private List<PostDto> content;
    private  int pageNumber;
    private  int pageSize;
    private int totalElements;
    private int totalPages;
    private boolean lastPage;

}
