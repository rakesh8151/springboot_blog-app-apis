package com.example.blog.services.impl;

import com.example.blog.entities.Category;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.CategoryDto;
import com.example.blog.repositories.CategoryRepository;
import com.example.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=modelMapper.map(categoryDto, Category.class);
        Category categoryCreated=  categoryRepository.save(category);
        CategoryDto categoryDto1=modelMapper.map(categoryCreated,CategoryDto.class);
        return categoryDto1;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("CategoryId","id",categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category saveCategory=categoryRepository.save(category);
        CategoryDto categoryDto1=modelMapper.map(saveCategory, CategoryDto.class);

        return categoryDto1;
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("CategoryId","id",categoryId));
        CategoryDto categoryDto1=modelMapper.map(category, CategoryDto.class);
        return categoryDto1;

    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories=categoryRepository.findAll();
        List<CategoryDto> categoryDtos=categories.stream().map((category -> {
            return  modelMapper.map(category, CategoryDto.class);
        })).toList();
        return categoryDtos;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ","category id",categoryId));
        categoryRepository.delete(category);
    }
}
