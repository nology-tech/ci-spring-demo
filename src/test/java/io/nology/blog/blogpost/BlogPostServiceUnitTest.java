package io.nology.blog.blogpost;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import io.nology.blog.category.Category;
import io.nology.blog.category.CategoryService;
import io.nology.blog.common.exceptions.ServiceValidationException;

public class BlogPostServiceUnitTest {
    @Mock
    private BlogPostRepository repo;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ModelMapper mapper;

    @Spy
    @InjectMocks
    private BlogPostService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBlogPost_fails_whenTopicIsCovid() {
        // given
        CreateBlogPostDTO data = new CreateBlogPostDTO();
        data.setCategoryId(1L);

        BlogPost mockPost = new BlogPost();
        mockPost.setTitle("neer");
        Category mockCategory = new Category();
        mockCategory.setName("test");
        Optional<Category> result = Optional.of(mockCategory);

        // when
        when(mapper.map(data, BlogPost.class)).thenReturn(mockPost);
        when(categoryService.findById(1L)).thenReturn(result);
        // then
        assertThrows(ServiceValidationException.class, () -> service.createBlogPost(data));

        verify(repo, never()).save(any());
    }
}
