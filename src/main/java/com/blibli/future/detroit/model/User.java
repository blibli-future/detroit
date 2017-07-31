package com.blibli.future.detroit.model;


import com.blibli.future.detroit.model.enums.Gender;
import com.blibli.future.detroit.model.enums.UserType;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "detroit_users")
@SQLDelete(sql =
    "UPDATE detroit_users " +
    "SET deleted = true " +
    "WHERE id = ?")
@Where(clause = "deleted=false")
public class User extends BaseModel implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String fullname;
    private String nickname;
    private String email;
    private String password;
    private String teamLeader;
    private String dateOfBirth;
    private Gender gender;
    private String location;
    private String phoneNumber;
    private UserType userType;
    @OneToMany
    @JoinColumn(name = "email", referencedColumnName = "email")
    private List<UserRole> userRole;
    @ManyToOne
    private AgentChannel agentChannel;
    @ManyToOne
    private AgentPosition agentPosition;

    public boolean isReviewer() {
        return this.userType.equals(UserType.REVIEWER);
    }

    public boolean isAgent() {
        return this.userType.equals(UserType.AGENT);
    }

    public boolean isSuperAdmin() {
        return this.userType.equals(UserType.SUPER_ADMIN);
    }

    //Semua yang dibawah ini auto-generate
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (fullname != null ? !fullname.equals(user.fullname) : user.fullname != null) return false;
        if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (teamLeader != null ? !teamLeader.equals(user.teamLeader) : user.teamLeader != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(user.dateOfBirth) : user.dateOfBirth != null) return false;
        if (gender != null ? !gender.equals(user.gender) : user.gender != null) return false;
        if (location != null ? !location.equals(user.location) : user.location != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null) return false;
        if (userType != user.userType) return false;
        if (userRole != null ? !userRole.equals(user.userRole) : user.userRole != null) return false;
        if (agentChannel != null ? !agentChannel.equals(user.agentChannel) : user.agentChannel != null) return false;
        return agentPosition != null ? agentPosition.equals(user.agentPosition) : user.agentPosition == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fullname != null ? fullname.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (teamLeader != null ? teamLeader.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (agentChannel != null ? agentChannel.hashCode() : 0);
        result = 31 * result + (agentPosition != null ? agentPosition.hashCode() : 0);
        return result;
    }
}
