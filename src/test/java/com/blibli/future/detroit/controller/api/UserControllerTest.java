package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.model.request.NewUserRequest;
import com.blibli.future.detroit.service.UserService;
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

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles(value = "test")
public class UserControllerTest {
    @MockBean
    private UserService userService;

    @LocalServerPort
    private int serverPort;

    User user = new User();
    NewUserRequest request = new NewUserRequest();
    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {
        user.setId(1L);
        user.setFullname("Detroit Project");
        user.setNickname("Detroit");
        user.setEmail("detroit@gdn-commerce.com");
        user.setChannel("cs");
        user.setTeamLeader("1");
        user.setDateOfBirth("01/01/1996");
        user.setGender("M");
        user.setLocation("Jakarta");
        user.setPhoneNumber("123456789");
        user.setUserType(UserType.AGENT);

        request.setId(1L);
        request.setFullname("Detroit Project");
        request.setNickname("Detroit");
        request.setEmail("detroit@gdn-commerce.com");
        request.setChannel("cs");
        request.setTeamLeader("1");
        request.setDateOfBirth("01/01/1996");
        request.setGender("M");
        request.setLocation("Jakarta");
        request.setPhoneNumber("123456789");
        request.setUserType(UserType.AGENT);
    }

    @Test
    public void getAllUserTest() {
        when(userService.getAllUser()).thenReturn(Arrays.asList(user));

        given()
            .contentType("application/json")
            .when()
            .port(serverPort)
            .get(UserController.GET_ALL_USER)
            .then()
            .body(containsString("1"))
            .body(containsString("Detroit Project"))
            .body(containsString("Detroit"))
            .body(containsString("detroit@gdn-commerce.com"))
            .body(containsString("cs"))
            .body(containsString("01/01/1996"))
            .body(containsString("M"))
            .body(containsString("Jakarta"))
            .body(containsString("123456789"))
            .body(containsString("true"))
            .body(containsString("AGENT"))
            .statusCode(200);

        verify(userService).getAllUser();
    }

    @Test
    public void createUserTest() {
        String jsonRequest = "";
        try {
            jsonRequest = mapper.writeValueAsString(request);
        } catch (Exception e) {
            assert false;
        }
        when(userService.createUser(eq(request))).thenReturn(user);

        given()
            .contentType("application/json")
            .content(jsonRequest)
            .when()
            .port(serverPort)
            .post(UserController.CREATE_USER)
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(userService).createUser(eq(request));
    }

    @Test
    public void deleteUserTest() {
        when(userService.deleteUser(1L)).thenReturn(true);

        given()
            .contentType("application/json")
            .when()
            .port(serverPort)
            .delete(UserController.BASE_PATH + "/1")
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(userService).deleteUser(1L);
    }

    @Test
    public void getOneUserTest() {
        when(userService.getOneUser(1L)).thenReturn(user);

        given()
            .contentType("application/json")
            .when()
            .port(serverPort)
            .get(UserController.BASE_PATH + "/1")
            .then()
            .body(containsString("1"))
            .body(containsString("Detroit Project"))
            .body(containsString("Detroit"))
            .body(containsString("detroit@gdn-commerce.com"))
            .body(containsString("cs"))
            .body(containsString("01/01/1996"))
            .body(containsString("M"))
            .body(containsString("Jakarta"))
            .body(containsString("123456789"))
            .body(containsString("true"))
            .body(containsString("AGENT"))
            .statusCode(200);

        verify(userService).getOneUser(1L);
    }

    @Test
    public void updateUserTest() {
        String jsonRequest = "";
        try {
            jsonRequest = mapper.writeValueAsString(request);
        } catch (Exception e) {
            assert false;
        }
        when(userService.updateUser(eq(1L), eq(request))).thenReturn(true);

        given()
            .contentType("application/json")
            .content(jsonRequest)
            .when()
            .port(serverPort)
            .patch(UserController.BASE_PATH + "/1")
            .then()
            .body(containsString("true"))
            .statusCode(200);

        verify(userService).updateUser(eq(1L), eq(request));
    }

}
