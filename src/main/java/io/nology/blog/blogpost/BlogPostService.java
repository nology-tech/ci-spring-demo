package io.nology.blog.blogpost;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nology.blog.category.Category;
import io.nology.blog.category.CategoryService;
import io.nology.blog.common.ValidationErrors;
import io.nology.blog.common.exceptions.ServiceValidationException;
import jakarta.validation.Valid;

@Service
public class BlogPostService {
    @Autowired
    private BlogPostRepository repo;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    public BlogPost createBlogPost(@Valid CreateBlogPostDTO data) throws Exception {
        // BlogPost newPost = new BlogPost();
        // newPost.setTitle(data.getTitle().trim());
        // newPost.setCategory(data.getCategory().trim().toLowerCase());
        // newPost.setContent(data.getContent().trim());
        // Date now = new Date();
        // newPost.setCreatedAt(now);
        // newPost.setUpdatedAt(now);
        ValidationErrors errors = new ValidationErrors();
        BlogPost newPost = mapper.map(data, BlogPost.class);
        Optional<Category> categoryResult = this.categoryService.findById(data.getCategoryId());

        if (newPost.getTitle().toLowerCase().contains("covid")) {
            errors.addError("title", "Forbidden topic");
        }

        if (categoryResult.isEmpty()) {
            errors.addError("category", String.format("Category with id %s does not exist", data.getCategoryId()));
        } else {
            newPost.setCategory(categoryResult.get());
        }

        if (errors.hasErrors()) {
            throw new ServiceValidationException(errors);
        }

        return this.repo.save(newPost);

    }

    public List<BlogPost> findAll() {
        return this.repo.findAll();
    }

    public Optional<BlogPost> findById(Long id) {
        return this.repo.findById(id);
    }

    public Optional<BlogPost> updateBlogPostById(Long id, @Valid UpdateBlogPostDTO data) throws Exception {
        Optional<BlogPost> result = this.findById(id);
        if (result.isEmpty()) {
            return result;
        }
        BlogPost foundPost = result.get();
        // if (data.getTitle() != null) {
        // foundPost.setTitle(data.getTitle().trim());
        // }
        // if (data.getContent() != null) {
        // foundPost.setContent(data.getContent().trim());
        // }
        // if (data.getCategory() != null) {
        // foundPost.setCategory(data.getCategory().trim().toLowerCase());
        // }
        // foundPost.setUpdatedAt(new Date());
        mapper.map(data, foundPost);
        ValidationErrors errors = new ValidationErrors();
        if (data.getCategoryId() != null) {
            Optional<Category> categoryResult = this.categoryService.findById(data.getCategoryId());
            if (categoryResult.isEmpty()) {
                errors.addError("category", String.format("Category with id %s does not exist", data.getCategoryId()));
            } else {
                foundPost.setCategory(categoryResult.get());
            }
        }
        if (errors.hasErrors()) {
            throw new ServiceValidationException(errors);
        }
        BlogPost updatedPost = this.repo.save(foundPost);

        return Optional.of(updatedPost);
    }

    // delete that returns the thing we deleted

    // public Optional<BlogPost> deleteById(Long id) {
    // Optional<BlogPost> result = this.findById(id);
    // if (result.isEmpty()) {
    // return result;
    // }
    // this.repo.delete(result.get());
    // return result;

    // }

    public boolean deleteById(Long id) {
        Optional<BlogPost> result = this.findById(id);
        if (result.isEmpty()) {
            return false;
        }

        this.repo.delete(result.get());

        return true;

    }

}
