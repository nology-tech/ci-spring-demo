package io.nology.blog.blogpost;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateBlogPostDTO {
    @NotBlank
    @Length(min = 5)
    private String title;
    @NotBlank
    private String content;

    @NotNull
    @Min(1)
    private Long categoryId;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
