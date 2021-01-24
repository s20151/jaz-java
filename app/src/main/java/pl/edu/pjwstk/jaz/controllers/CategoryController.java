package pl.edu.pjwstk.jaz.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.requests.CategoryRequest;
import pl.edu.pjwstk.jaz.services.CategoryService;

import javax.transaction.Transactional;

@RestController
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    @Transactional
    public void createCategory(@RequestBody CategoryRequest categoryRequest){
    categoryService.createCategory(categoryRequest);
    }
}
