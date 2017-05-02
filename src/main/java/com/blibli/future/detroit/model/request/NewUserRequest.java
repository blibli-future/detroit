package com.blibli.future.detroit.model.request;

import java.io.Serializable;

import com.blibli.future.detroit.model.AgentChannel;
import com.blibli.future.detroit.model.AgentPosition;
import com.blibli.future.detroit.model.enums.UserType;

public class NewUserRequest implements Serializable {
    private Long id;
    private String fullname;
    private String nickname;
    private String email;
    private String channel;
    private String teamLeader;
    private String dateOfBirth;
    private String gender;
    private String location;
    private String phoneNumber;
    private AgentChannel agentChannel;
    private AgentPosition agentPosition;
    private UserType userType;

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

    public AgentChannel getAgentChannel() {
        return agentChannel;
    }

    public void setAgentChannel(AgentChannel agentChannel) {
        this.agentChannel = agentChannel;
    }

    public AgentPosition getAgentPosition() {
        return agentPosition;
    }

    public void setAgentPosition(AgentPosition agentPosition) {
        this.agentPosition = agentPosition;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewUserRequest that = (NewUserRequest) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fullname != null ? !fullname.equals(that.fullname) : that.fullname != null) return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (channel != null ? !channel.equals(that.channel) : that.channel != null) return false;
        if (teamLeader != null ? !teamLeader.equals(that.teamLeader) : that.teamLeader != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (agentChannel != null ? !agentChannel.equals(that.agentChannel) : that.agentChannel != null) return false;
        if (agentPosition != null ? !agentPosition.equals(that.agentPosition) : that.agentPosition != null)
            return false;
        return userType == that.userType;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fullname != null ? fullname.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        result = 31 * result + (teamLeader != null ? teamLeader.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (agentChannel != null ? agentChannel.hashCode() : 0);
        result = 31 * result + (agentPosition != null ? agentPosition.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        return result;
    }
}
