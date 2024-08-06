package com.example.bloggingapp.controllers;

import com.example.bloggingapp.payloads.ApiResponse;
import com.example.bloggingapp.payloads.CategoryDto;
import com.example.bloggingapp.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
       CategoryDto createCategory= this.categoryService.createCategory(categoryDto);
       return  new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{categoryId}")
    public  ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto ,@PathVariable Integer categoryId){
        CategoryDto updateCategory= this.categoryService.updateCategory(categoryDto,categoryId);
        return  new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
    }


    //delete

    @DeleteMapping("/{categoryId}")
    public  ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
         this.categoryService.deleteCategory(categoryId);
        return  new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully!!",true), HttpStatus.OK);
    }
    @GetMapping("/{categoryId}")
    public  ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId){
        CategoryDto getCategoryDto=this.categoryService.getCategory(categoryId);
        return  new ResponseEntity<CategoryDto>(getCategoryDto,HttpStatus.OK);
    }

    //single category
    @GetMapping("/")
    public  ResponseEntity<List<CategoryDto>> getSingleCategory(){
      List<CategoryDto> getCategories=this.categoryService.getALLCategory();
        return ResponseEntity.ok(getCategories);

    }



}
