package pl.edu.pjwstk.jaz.controllers;

import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.requests.CategoryRequest;
import pl.edu.pjwstk.jaz.requests.SectionRequest;
import pl.edu.pjwstk.jaz.services.CategoryService;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@RestController
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    @Transactional
    public void createCategory(@RequestBody CategoryRequest categoryRequest, HttpServletResponse response){
    categoryService.createCategory(categoryRequest, response);
    }
    @PutMapping("/category/{categoryID}")
    @Transactional
    public void createSection(@PathVariable("categoryID") Long id, @RequestBody CategoryRequest categoryRequest, HttpServletResponse response){
        categoryService.editCategory(id,categoryRequest, response);
    }
}
