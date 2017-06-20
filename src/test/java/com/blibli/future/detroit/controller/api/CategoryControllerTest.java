package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.request.NewCategoryRequest;
import com.blibli.future.detroit.model.request.SimpleListRequest;
import com.blibli.future.detroit.service.CategoryService;
import com.blibli.future.detroit.util.Constant;
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

    private static final Long PARAMETER_ID = 1L;
    ObjectMapper mapper = new ObjectMapper();
    Parameter parameter = new Parameter();
    Category Category = new Category();
    Category category2 = new Category();
    NewCategoryRequest request = new NewCategoryRequest();
    NewCategoryRequest request2 = new NewCategoryRequest();
    SimpleListRequest<NewCategoryRequest> listRequest = new SimpleListRequest<>();


    @Before
    public void setUp() {
        parameter.setId(PARAMETER_ID);
        parameter.setDescription("Lorem ipsum");
        parameter.setName("Parameter");
        parameter.setActive(true);
        parameter.setWeight(100f);

        Category.setId(1L);
        Category.setDescription("Lorem Ipsum");
        Category.setName("Kategori1");
        Category.setWeight(100f);
        Category.setParameter(parameter);

        category2.setId(2L);
        category2.setDescription("Lorem Ipsum");
        category2.setName("Kategori2");
        category2.setWeight(100f);
        category2.setParameter(parameter);

        request.setId(1L);
        request.setDescription("Lorem Ipsum");
        request.setName("Kategori1");
        request.setWeight(100F);
        request.setParameter(parameter);

        request2.setId(2L);
        request2.setDescription("Lorem Ipsum");
        request2.setName("Kategori2");
        request2.setWeight(100f);
        request2.setParameter(parameter);

        List<NewCategoryRequest> list = new ArrayList<>();
        list.add(request);
        list.add(request2);
        listRequest.setList(list);
    }

    @Test
    public void createCategory() {
        String jsonRequest = "";
        try {
            jsonRequest = mapper.writeValueAsString(request);
        } catch (Exception e) {
            assert false;
        }
        when(categoryService.createCategory(eq(request))).thenReturn(Category);

        given()
            .contentType("application/json")
            .content(jsonRequest)
            .when()
            .port(serverPort)
            .post(Constant.API_PATH_V1 + "/parameters" + "/" + PARAMETER_ID + "/categories")
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(categoryService).createCategory(eq(request));
    }

    @Test
    public void batchUpdateParameter() {
        String jsonRequest = "";
        try {
            jsonRequest = mapper.writeValueAsString(listRequest);
        } catch (Exception e) {
            assert false;
        }
        when(categoryService.batchUpdateCategory(eq(listRequest), eq(PARAMETER_ID))).thenReturn(true);

        given()
            .contentType("application/json")
            .content(jsonRequest)
            .when()
            .port(serverPort)
            .patch(Constant.API_PATH_V1 + "/parameters" + "/" + PARAMETER_ID + "/categories")
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(categoryService).batchUpdateCategory(eq(listRequest), eq(PARAMETER_ID));
    }

    @Test
    public void batchUpdateParameter_checkWeightPercentage() {
        String jsonRequest = "";
        try {
            jsonRequest = mapper.writeValueAsString(listRequest);
            System.out.println(jsonRequest);
        } catch (Exception e) {
            assert false;
        }
        when(categoryService.batchUpdateCategory(eq(listRequest), eq(PARAMETER_ID))).thenThrow(new WeightPercentageNotValid());

        given()
            .contentType("application/json")
            .content(jsonRequest)
            .when()
            .port(serverPort)
            .patch(Constant.API_PATH_V1 + "/parameters" + "/" + PARAMETER_ID + "/categories")
            .then()
            .body(containsString("false"))
            .statusCode(200);

        verify(categoryService).batchUpdateCategory(eq(listRequest), eq(PARAMETER_ID));
    }

    @Test
    public void deleteParameter() {
        when(categoryService.deleteCategory(1L)).thenReturn(true);

        given()
            .contentType("application/json")
            .when()
            .port(serverPort)
            .delete(Constant.API_PATH_V1 + "/parameters" + "/" + PARAMETER_ID + "/categories" + "/1")
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(categoryService).deleteCategory(1L);
    }
}
