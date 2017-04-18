package com.blibli.future.detroit.model;


import com.blibli.future.detroit.model.enums.UserType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "detroit_users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String fullname;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (fullname != null ? !fullname.equals(user.fullname) : user.fullname != null) return false;
        if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null) return false;
        if (!email.equals(user.email)) return false;
        if (channel != null ? !channel.equals(user.channel) : user.channel != null) return false;
        if (teamLeader != null ? !teamLeader.equals(user.teamLeader) : user.teamLeader != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(user.dateOfBirth) : user.dateOfBirth != null) return false;
        if (gender != null ? !gender.equals(user.gender) : user.gender != null) return false;
        if (location != null ? !location.equals(user.location) : user.location != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null) return false;
        return userType == user.userType;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (fullname != null ? fullname.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + email.hashCode();
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        result = 31 * result + (teamLeader != null ? teamLeader.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + userType.hashCode();
        return result;
    }

    private String nickname;
    private String email;
    private String channel;
    private String teamLeader;
    private String dateOfBirth;
    private String gender;
    private String location;
    private String phoneNumber;
    private UserType userType;

    public Long getId() {
        return id;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isReviewer() {
        return this.userType.equals(UserType.REVIEWER);
    }

    public boolean isAgent() {
        return this.userType.equals(UserType.AGENT);
    }

    public boolean isSuperAdmin() {
        return this.userType.equals(UserType.SUPER_ADMIN);
    }
}
