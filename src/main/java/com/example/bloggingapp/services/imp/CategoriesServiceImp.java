package com.example.bloggingapp.services.imp;

import com.example.bloggingapp.entites.Category;
import com.example.bloggingapp.exception.ResourceNotFoundException;
import com.example.bloggingapp.payloads.CategoryDto;
import com.example.bloggingapp.repository.CategoryRepo;
import com.example.bloggingapp.services.CategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoriesServiceImp implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;


    //create
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category addedCat = this.categoryRepo.save(category);
        return this.modelMapper.map(addedCat, CategoryDto.class);
    }

    //update
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow((() -> new ResourceNotFoundException("Category", "Category Id", categoryId)));

        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedCat, CategoryDto.class);
    }

    //Delete
    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getALLCategory() {
        List<Category> category = this.categoryRepo.findAll();
        return category.stream().map(category1 -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }
}
