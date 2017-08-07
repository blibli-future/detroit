package com.blibli.future.detroit.model.dto;

import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.UserRole;

import java.util.ArrayList;
import java.util.List;

public class ReviewerDto extends UserDto {

    List<String> reviewerRole;

    Boolean isSuperAdmin;

    public ReviewerDto() {}

    public ReviewerDto(User user) {
        super(user);

        this.isSuperAdmin = user.isSuperAdmin();
        this.reviewerRole = new ArrayList<>();
        for(UserRole role: user.getUserRole()) {
            if (role.getRole().startsWith("PARAM ")) {
                this.reviewerRole.add(role.getRole());
            }
        }
    }

    public List<String> getReviewerRole() {
        return reviewerRole;
    }

    public void setReviewerRole(List<String> reviewerRole) {
        this.reviewerRole = reviewerRole;
    }

    public Boolean getSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(Boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }
}
