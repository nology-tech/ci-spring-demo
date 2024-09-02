package io.nology.blog.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CategoryEndToEndTest {
    @LocalServerPort
    private int port;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        categoryRepository.deleteAll();
        // be careful not to save invalid data

        Category category1 = new Category();
        category1.setName("code");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("art");
        categoryRepository.save(category2);
    }

    @Test
    public void getAllCategories() {
        given()
                .when()
                .get("/categories")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2))
                .body("name", hasItems("code", "art"));
        // .body(matchesJsonSchemaInClasspath("io/nology/blog/category/schemas/categories-schema.json"));
    }

    @Test
    public void createCategory_success() {
        // set up a DTO
        CreateCategoryDTO data = new CreateCategoryDTO();
        data.setName("new category");
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/categories")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("new category"))
                .body("id", notNullValue());
        // .body(matchesJsonSchemaInClasspath("io/nology/blog/category/schemas/category-schema.json"));

        // check this category is in find all

        given()
                .when()
                .get("/categories")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(3))
                .body("name", hasItems("code", "art", "new category"));
    }

    @Test
    public void createCategory_existingCategory_failure() {
        // set up DTO
        CreateCategoryDTO data = new CreateCategoryDTO();
        data.setName("code");
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/categories")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                // .body(matchesJsonSchemaInClasspath(
                // "io/nology/blog/category/schemas/existing-category-error-schema.json"))
                .body("errors.name[0]", equalTo("category with name 'code' already exists"));
    }

    @Test
    public void createCategory_emptyCategory_failure() {
        CreateCategoryDTO data = new CreateCategoryDTO();
        data.setName("");
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/categories")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void createCategory_nullCategory_failure() {
        CreateCategoryDTO data = new CreateCategoryDTO();
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/categories")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }
}
