package com.pivotree.appzone.controller;

import com.pivotree.appzone.entity.Customer;
import com.pivotree.appzone.enums.UserType;
import com.pivotree.appzone.intemplate.CategoryInput;
import com.pivotree.appzone.intemplate.CategorySearch;
import com.pivotree.appzone.intemplate.UsernameInput;
import com.pivotree.appzone.outtemplate.CategoryOutput;
import com.pivotree.appzone.service.CategoryService;
import com.pivotree.appzone.entity.Category;
import com.pivotree.appzone.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CustomerService customerService;

    /**
     * Adds a new category to the system
     * @param categoryInput the input data for the new category
     * @return the newly created category
     */

    @PostMapping("/addCategory")
    public ResponseEntity<Object> addCategory(@Valid @RequestBody CategoryInput categoryInput){
        Category category = categoryService.addCategory(categoryInput);
        CategoryOutput categoryOutput= convertCategoryToCategoryOutput(category);
        return new ResponseEntity<>(categoryOutput, HttpStatusCode.valueOf(201));
    }


    @GetMapping("/allCategory")
    public ResponseEntity<Object> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoriesPage = categoryService.getAllCategories(pageable);
        return new ResponseEntity<>(categoriesPage, HttpStatus.OK);
    }


    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable("id") Long id, @Valid @RequestBody CategoryInput categoryInput) {
        Category updatedCategory = categoryService.updateCategory(id, categoryInput);
        if (updatedCategory == null) {
            return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
        }
        CategoryOutput categoryOutput = convertCategoryToCategoryOutput(updatedCategory);
        return new ResponseEntity<>(categoryOutput, HttpStatus.OK);
    }

    private CategoryOutput convertCategoryToCategoryOutput(Category category) {
        return new CategoryOutput(Math.toIntExact(category.getId()), category.getCategoryName(), category.getDescription());
    }


    @PostMapping("/categorySearch")
    public ResponseEntity<Object> getCategoryByName(@RequestBody CategorySearch apiInput) {
        Category catSearch = categoryService.findCategoryName(apiInput.getCategoryName());
        return new ResponseEntity<>(catSearch, HttpStatus.OK);
    }
}
