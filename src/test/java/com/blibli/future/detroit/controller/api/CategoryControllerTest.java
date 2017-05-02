package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.request.NewParameterRequest;
import com.blibli.future.detroit.model.request.SimpleListRequest;
import com.blibli.future.detroit.service.ParameterService;
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
    private ParameterService parameterService;

    @LocalServerPort
    private int serverPort;

    private static final Long CATEGORY_ID = 1L;
    ObjectMapper mapper = new ObjectMapper();
    Parameter parameter = new Parameter();
    Category Category = new Category();
    Category category2 = new Category();
    NewParameterRequest request = new NewParameterRequest();
    SimpleListRequest<Category> listRequest = new SimpleListRequest<>();


    @Before
    public void setUp() {
        parameter.setId(CATEGORY_ID);
        parameter.setDescription("Lorem ipsum");
        parameter.setName("Kategori");
        parameter.setActive(true);
        parameter.setWeight(100f);

        Category.setId(1L);
        Category.setDescription("Lorem Ipsum");
        Category.setName("Parameter1");
        Category.setWeight(100f);
        Category.setParameter(parameter);

        category2.setId(2L);
        category2.setDescription("Lorem Ipsum");
        category2.setName("Parameter2");
        category2.setWeight(100f);
        category2.setParameter(parameter);

        request.setId(1L);
        request.setDescription("Lorem Ipsum");
        request.setName("Parameter1");
        request.setWeight(100F);
        request.setParameter(parameter);

        List<Category> list = new ArrayList<>();
        list.add(Category);
        list.add(category2);
        listRequest.setList(list);
    }

    @Test
    public void createParameter() {
        String jsonRequest = "";
        try {
            jsonRequest = mapper.writeValueAsString(request);
        } catch (Exception e) {
            assert false;
        }
        when(parameterService.createParameter(eq(request), eq(CATEGORY_ID))).thenReturn(Category);

        given()
            .contentType("application/json")
            .content(jsonRequest)
            .when()
            .port(serverPort)
            .post(ParameterController.BASE_PATH + "/" + CATEGORY_ID + ParameterController.END_PATH)
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(parameterService).createParameter(eq(request), eq(CATEGORY_ID));
    }

    @Test
    public void batchUpdateParameter() {
        String jsonRequest = "";
        try {
            jsonRequest = mapper.writeValueAsString(listRequest);
        } catch (Exception e) {
            assert false;
        }
        when(parameterService.batchUpdateParameter(eq(listRequest), eq(CATEGORY_ID))).thenReturn(true);

        given()
            .contentType("application/json")
            .content(jsonRequest)
            .when()
            .port(serverPort)
            .patch(ParameterController.BASE_PATH + "/" + CATEGORY_ID + ParameterController.END_PATH)
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(parameterService).batchUpdateParameter(eq(listRequest), eq(CATEGORY_ID));
    }

    @Test
    public void batchUpdateParameter_checkWeightPercentage() {
        String jsonRequest = "";
        try {
            jsonRequest = mapper.writeValueAsString(listRequest);
        } catch (Exception e) {
            assert false;
        }
        when(parameterService.batchUpdateParameter(eq(listRequest), eq(CATEGORY_ID))).thenThrow(new WeightPercentageNotValid());

        given()
            .contentType("application/json")
            .content(jsonRequest)
            .when()
            .port(serverPort)
            .patch(ParameterController.BASE_PATH + "/" + CATEGORY_ID + ParameterController.END_PATH)
            .then()
            .body(containsString("false"))
            .statusCode(200);

        verify(parameterService).batchUpdateParameter(eq(listRequest), eq(CATEGORY_ID));
    }

    @Test
    public void deleteParameter() {
        when(parameterService.deleteParameter(1L)).thenReturn(true);

        given()
            .contentType("application/json")
            .when()
            .port(serverPort)
            .delete(ParameterController.BASE_PATH + "/" + CATEGORY_ID + ParameterController.END_PATH + "/1")
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(parameterService).deleteParameter(1L);
    }
}
