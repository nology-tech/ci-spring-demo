package io.nology.blog.category;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryDTO data) throws Exception {
        // you might want to do extra layer of exception handling in the controller
        // try {
        // Category newCategory = this.categoryService.create(data);
        // return new ResponseEntity<Category>(newCategory, HttpStatus.CREATED);
        // } catch (Exception e) {
        // String exceptionClass = e.getClass().getSimpleName();
        // if (exceptionClass.equals("ServiceValidationException")) {
        // var err = (ServiceValidationException) e;
        // throw new BadRequestException(err.getErrors().toString());
        // } else {
        // throw new Exception("Someting else went wrong");
        // }
        // }

        Category newCategory = this.categoryService.create(data);
        return new ResponseEntity<Category>(newCategory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> allCategories = this.categoryService.findAll();
        return new ResponseEntity<List<Category>>(allCategories, HttpStatus.OK);
    }

}
