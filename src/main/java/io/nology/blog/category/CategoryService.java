package io.nology.blog.category;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nology.blog.common.ValidationErrors;
import io.nology.blog.common.exceptions.ServiceValidationException;
import jakarta.validation.Valid;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;
    @Autowired
    private ModelMapper mapper;

    public Category create(@Valid CreateCategoryDTO data) throws Exception {
        ValidationErrors errors = new ValidationErrors();
        String formattedName = data.getName().trim().toLowerCase();
        if (repo.existsByName(formattedName)) {
            errors.addError("name", String.format("category with name '%s' already exists", formattedName));
        }
        Category newCategory = mapper.map(data, Category.class);
        if (errors.hasErrors()) {
            throw new ServiceValidationException(errors);
        }
        return this.repo.save(newCategory);

    }

    public List<Category> findAll() {
        return this.repo.findAll();

    }

    public Optional<Category> findById(Long categoryId) {
        return this.repo.findById(categoryId);
    }

}
