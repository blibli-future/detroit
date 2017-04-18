package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.Parameter;
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
public class ParameterControllerTest {

    @MockBean
    private ParameterService parameterService;

    @LocalServerPort
    private int serverPort;

    private static final Long CATEGORY_ID = 1L;
    ObjectMapper mapper = new ObjectMapper();
    Category category = new Category();
    Parameter parameter = new Parameter();
    Parameter parameter2 = new Parameter();
    NewParameterRequest request = new NewParameterRequest();
    SimpleListRequest<Parameter> listRequest = new SimpleListRequest<>();


    @Before
    public void setUp() {
        category.setId(CATEGORY_ID);
        category.setDescription("Lorem ipsum");
        category.setName("Kategori");
        category.setActive(true);
        category.setWeight(100f);

        parameter.setId(1L);
        parameter.setDescription("Lorem Ipsum");
        parameter.setName("Parameter1");
        parameter.setWeight(100f);
        parameter.setCategory(category);

        parameter2.setId(2L);
        parameter2.setDescription("Lorem Ipsum");
        parameter2.setName("Parameter2");
        parameter2.setWeight(100f);
        parameter2.setCategory(category);

        request.setId(1L);
        request.setDescription("Lorem Ipsum");
        request.setName("Parameter1");
        request.setWeight(100F);
        request.setCategory(category);

        List<Parameter> list = new ArrayList<>();
        list.add(parameter);
        list.add(parameter2);
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
        when(parameterService.createParameter(eq(request), eq(CATEGORY_ID))).thenReturn(parameter);

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
