package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.model.request.NewUserRequest;
import com.blibli.future.detroit.model.request.SimpleListRequest;
import com.blibli.future.detroit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getOneUser(long userId) {
        return userRepository.findOne(userId);
    }

    public User createUser(NewUserRequest request) {
        User newUser = new User();
        newUser.setFullname(request.getFullname());
        newUser.setNickname(request.getNickname());
        newUser.setEmail(request.getEmail());
        newUser.setChannel(request.getChannel());
        newUser.setTeamLeader(request.getTeamLeader());
        newUser.setDateOfBirth(request.getDateOfBirth());
        newUser.setGender(request.getGender());
        newUser.setLocation(request.getLocation());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setRole(request.getUserType());
        userRepository.save(newUser);

        return newUser;
    }

    public boolean deleteUser(Long userId) {
        userRepository.delete(userId);
        return true;
    }

    public boolean updateUser(Long userId, User request) {
        User user = userRepository.findOne(userId);
        user.setFullname(request.getFullname());
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setChannel(request.getChannel());
        user.setTeamLeader(request.getTeamLeader());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        user.setLocation(request.getLocation());
        user.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(user);

        return true;
    }
}
