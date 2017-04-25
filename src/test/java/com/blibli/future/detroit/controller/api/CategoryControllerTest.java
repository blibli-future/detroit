package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.request.NewCategoryRequest;
import com.blibli.future.detroit.model.request.SimpleListRequest;
import com.blibli.future.detroit.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles(value = "test")
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @LocalServerPort
    private int serverPort;

    /* Deklarasi untuk data yang dipakai dalam testing, misal insert dll */
    Category category = new Category();
    NewCategoryRequest request = new NewCategoryRequest();
    ObjectMapper mapper = new ObjectMapper();
    Category category2 = new Category();

    SimpleListRequest<Category> listRequest = new SimpleListRequest<>();

    @Before
    public void setUp() {
        category.setId(1L);
        category.setDescription("Lorem ipsum");
        category.setName("Kategori");
        category.setActive(true);
        category.setWeight(100F);

        category2.setId(2L);
        category2.setDescription("Lorem ipsum");
        category2.setName("Kategori");
        category2.setActive(true);
        category2.setWeight(100F);

        request.setId(1L);
        request.setDescription("Lorem ipsum");
        request.setName("Kategori");
        request.setActive(true);
        request.setWeight(100f);

        List<Category> list = new ArrayList<>();
        list.add(category);
        list.add(category2);
        listRequest.setList(list);
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
        String jsonRequest = "";
        try {
            jsonRequest = mapper.writeValueAsString(request);
        } catch (Exception e) {
            assert false;
        }
        when(categoryService.createCategory(eq(request))).thenReturn(category);

        given()
            .contentType("application/json")
            .content(jsonRequest)
            .when()
            .port(serverPort)
            .post(CategoryController.CREATE_CATEGORY)
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(categoryService).createCategory(eq(request));
    }

    @Test
    public void deleteCategoryTest() {
        when(categoryService.deleteCategory(1L)).thenReturn(true);

        given()
            .contentType("application/json")
            .when()
            .port(serverPort)
            .delete(CategoryController.BASE_PATH + "/1")
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(categoryService).deleteCategory(1L);
    }

    @Test
    public void getOneCategory() {
        when(categoryService.getOneCategory(1L)).thenReturn(category);

        given()
            .contentType("application/json")
            .when()
            .port(serverPort)
            .get(CategoryController.BASE_PATH + "/1")
            .then()
            .body(containsString("Lorem ipsum"))
            .body(containsString("Kategori"))
            .body(containsString("true"))
            .body(containsString("100"))
            .statusCode(200);

        verify(categoryService).getOneCategory(1L);
    }

    @Test
    public void batchUpdateCategory() {
        String jsonRequest = "";
        try {
            jsonRequest = mapper.writeValueAsString(listRequest);
        } catch (Exception e) {
            assert false;
        }
        when(categoryService.batchUpdateCategory(eq(listRequest))).thenReturn(true);

        given()
            .contentType("application/json")
            .content(jsonRequest)
            .when()
            .port(serverPort)
            .patch(CategoryController.BATCH_UPDATE_CATEGORY)
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(categoryService).batchUpdateCategory(eq(listRequest));
    }

    @Test
    public void batchUpdateCategory_checkWeightPercentage() {
        String jsonRequest = "";
        try {
            jsonRequest = mapper.writeValueAsString(listRequest);
        } catch (Exception e) {
            assert false;
        }
       when(categoryService.batchUpdateCategory(eq(listRequest))).thenThrow(new WeightPercentageNotValid());

        given()
            .contentType("application/json")
            .content(jsonRequest)
            .when()
            .port(serverPort)
            .patch(CategoryController.BATCH_UPDATE_CATEGORY)
            .then()
            .body(containsString("false"))
            .statusCode(200);

        verify(categoryService).batchUpdateCategory(eq(listRequest));
    }

}
