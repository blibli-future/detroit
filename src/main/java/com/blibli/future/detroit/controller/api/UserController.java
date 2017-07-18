package com.blibli.future.detroit.controller.api;


import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.dto.AgentDto;
import com.blibli.future.detroit.model.dto.ReviewerDto;
import com.blibli.future.detroit.model.dto.UserDto;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.model.request.NewUserRequest;
import com.blibli.future.detroit.model.response.BaseRestListResponse;
import com.blibli.future.detroit.model.response.BaseRestResponse;
import com.blibli.future.detroit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
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

    @PostMapping(CHECK_AUTH)
    @ResponseBody
    public BaseRestResponse<Boolean> checkAuth() {
        return new BaseRestResponse<>(true);
    }

    @GetMapping(GET_ALL_USER)
    public BaseRestListResponse<UserDto> getAllUsers(
          @RequestParam(required = false) UserType type) {
        List<User> allUser = userService.getAllUser(type);
        List<UserDto> response;
        if (type != null && type == UserType.AGENT) {
            response = allUser.stream().map(AgentDto::new).collect(Collectors.toList());
        } else {
            response = allUser.stream().map(UserDto::new).collect(Collectors.toList());
        }
        return new BaseRestListResponse<>(response);
    }

    @PostMapping(CREATE_USER)
    public BaseRestResponse createUser(@RequestBody NewUserRequest request) {
        userService.createUser(request);
        return new BaseRestResponse();
    }

    @DeleteMapping(DELETE_USER)
    public BaseRestResponse deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new BaseRestResponse();
    }

    @GetMapping(GET_ONE_USER)
    public BaseRestResponse<UserDto> getOneUser(@PathVariable Long userId) {
        User user = userService.getOneUser(userId);
        UserDto response;

        if (user.isReviewer() || user.isSuperAdmin()) {
            response = new ReviewerDto(user);
        } else {
            response = new AgentDto(user);
        }

        return new BaseRestResponse<>(response);
    }

    @PatchMapping(UPDATE_USER)
    public BaseRestResponse updateUser(@PathVariable Long userId, @RequestBody NewUserRequest request) {
        userService.updateUser(userId, request);
        return new BaseRestResponse();
    }
}
