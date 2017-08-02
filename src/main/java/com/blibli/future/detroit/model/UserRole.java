package com.blibli.future.detroit.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class UserRole extends BaseModel implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private String email;
    private String role;

    public UserRole() {
    }

    public UserRole(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public UserRole(User user, Parameter parameter) {
        this.email = user.getEmail();
        this.role = "PARAM " + parameter.getName();
    }

    public UserRole(User user, String role) {
        this.email = user.getEmail();
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
