package com.moriya.project_moriya_java.controller;

import com.moriya.project_moriya_java.model.Categories;
import com.moriya.project_moriya_java.service.CategoriesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(value = "http://localhost:5173", allowCredentials = "true")
public class CategoriesController {

    private CategoriesRepository categoriesRepository;

    public CategoriesController(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;

    }

    // Get all categories
    @GetMapping("/getAllCategories")
    public ResponseEntity<List<Categories>> getAllCategories() {
        return new ResponseEntity<>(categoriesRepository.findAll(), HttpStatus.OK);
    }

    // Get a specific category by id
    @GetMapping("/getCategory/{id}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable Long id) {
        Categories category = categoriesRepository.findById(id).orElse(null);
        if (category == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // Add a new category
    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/addCategory")
    public ResponseEntity<Categories> addCategory(@RequestBody Categories category) {
        System.out.println("User is authorized: " + SecurityContextHolder.getContext().getAuthentication().getName());
        Categories newCategory = categoriesRepository.save(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }


    // Update an existing category
    @PutMapping("/updateCategory/{id}")
    public ResponseEntity updateCategory(@PathVariable Categories category, @PathVariable Long id) {
        if (!id.equals(category.getId())) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        categoriesRepository.save(category);
        return new ResponseEntity(HttpStatus.OK);
    }

    // Delete a category
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        categoriesRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
