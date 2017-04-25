package com.blibli.future.detroit.repository;


import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUserType(UserType userType);
    User findByEmail(String email);
}
