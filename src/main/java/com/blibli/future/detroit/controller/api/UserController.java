package com.blibli.future.detroit.controller.api;


import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.model.request.NewUserRequest;
import com.blibli.future.detroit.model.response.BaseRestListResponse;
import com.blibli.future.detroit.model.response.BaseRestResponse;
import com.blibli.future.detroit.service.UserService;
import com.blibli.future.detroit.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    public static final String BASE_PATH = "/api/v1/users";
    public static final String GET_ALL_USER = BASE_PATH;
    public static final String CREATE_USER = BASE_PATH;
    public static final String DELETE_USER = BASE_PATH + "/{userId}";
    public static final String GET_ONE_USER = BASE_PATH + "/{userId}";
    public static final String UPDATE_USER = BASE_PATH + "/{userId}";
    public static final String CHECK_AUTH = BASE_PATH + "/login";

    @Autowired
    private UserService userService;

    @PostMapping(value = CHECK_AUTH)
    @ResponseBody
    public BaseRestResponse<Boolean> checkAuth() {
        return new BaseRestResponse<>(true);
    }

    @GetMapping(value = GET_ALL_USER, produces = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestListResponse<User> getAllUsers(
        @RequestParam(required = false) UserType type) {
        List<User> allUser = userService.getAllUser(type);
        return new BaseRestListResponse<>(allUser);
    }

    @PostMapping(value = CREATE_USER, produces = Constant.API_MEDIA_TYPE, consumes = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse createUser(@RequestBody NewUserRequest request) {
        userService.createUser(request);
        return new BaseRestResponse();
    }

    @DeleteMapping(value = DELETE_USER, produces = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new BaseRestResponse();
    }

    @GetMapping(value = GET_ONE_USER, produces = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse<User> getOneUser(@PathVariable Long userId) {
        User user = userService.getOneUser(userId);
        return new BaseRestResponse<>(user);
    }

    @PatchMapping(value = UPDATE_USER, produces = Constant.API_MEDIA_TYPE, consumes = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse updateUser(@PathVariable Long userId, @RequestBody NewUserRequest request) {
        userService.updateUser(userId, request);
        return new BaseRestResponse();
    }
}
