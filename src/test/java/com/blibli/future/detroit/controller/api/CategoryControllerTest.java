package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.request.NewCategoryRequest;
import com.blibli.future.detroit.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @LocalServerPort
    private int serverPort;

    /* Deklarasi untuk data yang dipakai dalam testing, misal insert dll */
    Category category = new Category();
    NewCategoryRequest request = new NewCategoryRequest();

    @Before
    public void setUp() {
        category.setId(1L);
        category.setDescription("Lorem ipsum");
        category.setName("Kategori");
        category.setActive(true);
        category.setWeight(100);

        request.setId(1L);
        request.setDescription("Lorem ipsum");
        request.setName("Kategori");
        request.setActive(true);
        request.setWeight(100);
    }

    @Test
    public void getAllCategoriesTest() {
        when(categoryService.getAllCategory()).thenReturn(Arrays.asList(category));

        given()
            .contentType("application/json")
            .when()
            .port(serverPort)
            .get(CategoryController.GET_ALL_CATEGORY)
            .then()
            .body(containsString("Lorem ipsum"))
            .body(containsString("Kategori"))
            .body(containsString("true"))
            .body(containsString("100"))
            .statusCode(200);

        verify(categoryService).getAllCategory();
    }

    @Test
    public void createCategoryTest() {
        when(categoryService.createCategory(request)).thenReturn(true);

        given()
            .contentType("application/json")
            .content(request)
            .when()
            .port(serverPort)
            .post(CategoryController.CREATE_CATEGORY)
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(categoryService).createCategory(request);
    }
}
