package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.request.NewParameterRequest;
import com.blibli.future.detroit.model.request.SimpleListRequest;
import com.blibli.future.detroit.service.ParameterService;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ParameterControllerTest {

    @MockBean
    private ParameterService parameterService;

    @LocalServerPort
    private int serverPort;

    /* Deklarasi untuk data yang dipakai dalam testing, misal insert dll */
    Parameter parameter = new Parameter();
    NewParameterRequest request = new NewParameterRequest();
    NewParameterRequest request2 = new NewParameterRequest();
    ObjectMapper mapper = new ObjectMapper();
    Parameter parameter2 = new Parameter();

    SimpleListRequest<NewParameterRequest> listRequest = new SimpleListRequest<>();

    @Before
    public void setUp() {
        parameter.setId(1L);
        parameter.setDescription("Lorem ipsum");
        parameter.setName("Parameter");
        parameter.setActive(true);
        parameter.setWeight(100F);

        parameter2.setId(2L);
        parameter2.setDescription("Lorem ipsum");
        parameter2.setName("Parameter");
        parameter2.setActive(true);
        parameter2.setWeight(100F);

        request.setId(1L);
        request.setDescription("Lorem ipsum");
        request.setName("Parameter");
        request.setActive(true);
        request.setWeight(100f);

        request2.setId(2L);
        request2.setDescription("Lorem ipsum");
        request2.setName("Parameter");
        request2.setActive(true);
        request2.setWeight(100f);

        List<NewParameterRequest> list = new ArrayList<>();
        list.add(request);
        list.add(request2);
        listRequest.setList(list);
    }

    @Test
    public void getAllParametersTest() {
        when(parameterService.getAllParameter()).thenReturn(Arrays.asList(parameter));

        given()
            .contentType("application/json")
            .when()
            .port(serverPort)
            .get(ParameterController.GET_ALL_PARAMETER)
            .then()
            .body(containsString("Lorem ipsum"))
            .body(containsString("Parameter"))
            .body(containsString("true"))
            .body(containsString("100"))
            .statusCode(200);

        verify(parameterService).getAllParameter();
    }


    @Test
    public void createParameterTest() {
        String jsonRequest = "";
        try {
            jsonRequest = mapper.writeValueAsString(request);
        } catch (Exception e) {
            assert false;
        }
        when(parameterService.createParameter(eq(request))).thenReturn(parameter);

        given()
            .contentType("application/json")
            .content(jsonRequest)
            .when()
            .port(serverPort)
            .post(ParameterController.CREATE_PARAMETER)
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(parameterService).createParameter(eq(request));
    }

    @Test
    public void deleteParameterTest() {
        when(parameterService.deleteParameter(1L)).thenReturn(true);

        given()
            .contentType("application/json")
            .when()
            .port(serverPort)
            .delete(ParameterController.BASE_PATH + "/1")
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(parameterService).deleteParameter(1L);
    }

    @Test
    public void getOneParameter() {
        when(parameterService.getOneParameter(1L)).thenReturn(parameter);

        given()
            .contentType("application/json")
            .when()
            .port(serverPort)
            .get(ParameterController.BASE_PATH + "/1")
            .then()
            .body(containsString("Lorem ipsum"))
            .body(containsString("Parameter"))
            .body(containsString("true"))
            .body(containsString("100"))
            .statusCode(200);

        verify(parameterService).getOneParameter(1L);
    }

    @Test
    public void batchUpdateParameter() {
        String jsonRequest = "";
        try {
            jsonRequest = mapper.writeValueAsString(listRequest);
        } catch (Exception e) {
            assert false;
        }
        when(parameterService.batchUpdateParameter(eq(listRequest))).thenReturn(true);

        given()
            .contentType("application/json")
            .content(jsonRequest)
            .when()
            .port(serverPort)
            .patch(ParameterController.BATCH_UPDATE_PARAMETER)
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(parameterService).batchUpdateParameter(eq(listRequest));
    }

    @Test
    public void batchUpdateParameter_checkWeightPercentage() {
        String jsonRequest = "";
        try {
            jsonRequest = mapper.writeValueAsString(listRequest);
        } catch (Exception e) {
            assert false;
        }
       when(parameterService.batchUpdateParameter(eq(listRequest))).thenThrow(new WeightPercentageNotValid());

        given()
            .contentType("application/json")
            .content(jsonRequest)
            .when()
            .port(serverPort)
            .patch(ParameterController.BATCH_UPDATE_PARAMETER)
            .then()
            .body(containsString("false"))
            .statusCode(200);

        verify(parameterService).batchUpdateParameter(eq(listRequest));
    }

}
