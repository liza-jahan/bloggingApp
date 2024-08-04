package com.example.bloggingapp.services;


import com.example.bloggingapp.payloads.CategoryDto;
import com.example.bloggingapp.repository.CategoryRepo;

import java.util.List;

public interface CategoryService {
    //create
  CategoryDto createCategory(CategoryDto categoryDto);
    //update
    CategoryDto updateCategory(CategoryDto categoryDto,  Integer categoryId);

    //delete
    void deleteCategory(Integer categoryId );
    //get
    CategoryDto getCategory(Integer categoryId );

    //get All
    List<CategoryDto> getALLCategory();


}
