package com.pivotree.appzone.service;

import com.pivotree.appzone.intemplate.CategoryInput;
import com.pivotree.appzone.repository.CategoryManager;
import com.pivotree.appzone.entity.Category;
import com.pivotree.appzone.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryManager categoryManager;
    @Autowired
    private CategoryRepository categoryRepository;
    /**
     * Adds a new category to the system
     *
     * @param categoryInput the input data for the new category
     * @return the newly created category
     */

    public Category addCategory(CategoryInput categoryInput) {
        Category category = new Category(categoryInput.getCategoryName(), categoryInput.getDescription());
        categoryManager.save(category);
        return category;
    }

    @Transactional
    public Category updateCategory(Long categoryId, CategoryInput categoryInput) {
        Category existingCategory = categoryRepository.findByCategoryId(categoryId);
        if (Objects.isNull(existingCategory)) {
            throw new RuntimeException("Category not found with ID: " + categoryId);
        }
        existingCategory.setCategoryName(categoryInput.getCategoryName());
        existingCategory.setDescription(categoryInput.getDescription());
        categoryManager.save(existingCategory);
        return existingCategory;
    }

    public Category getCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        return categoryOptional.orElse(null);
    }
    public List<Category> getCategory(){
        return categoryRepository.findAll();
    }


    public Page<Category> getAllCategories(Pageable pageable) {

        return categoryRepository.findAll(pageable);
    }

    @Transactional
    public Category findCategoryName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

}
