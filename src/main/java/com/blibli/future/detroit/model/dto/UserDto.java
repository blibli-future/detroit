package com.blibli.future.detroit.model.dto;

import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.enums.Gender;
import com.blibli.future.detroit.model.enums.UserType;

import java.io.Serializable;

public class UserDto implements Serializable {

    Long id;
    String fullname;
    String nickname;
    String email;
    String dateOfBirth;
    Gender gender;
    String location;
    String phoneNumber;
    UserType userRole;

    public UserDto() {}

    public UserDto(User user) {
        this.id = user.getId();
        this.fullname = user.getFullname();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.dateOfBirth = user.getDateOfBirth();
        this.gender = user.getGender();
        this.location = user.getLocation();
        this.phoneNumber = user.getPhoneNumber();
        this.userRole = user.getUserType();
    }


    // AUTO GENERATED

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserType getUserRole() {
        return userRole;
    }

    public void setUserRole(UserType userRole) {
        this.userRole = userRole;
    }
}
