package com.blibli.future.detroit.repository;


import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Stream;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUserType(UserType userType);
    User findByEmail(String email);

    @Query("select count(u) from User u join u.userRole ur where ur.role='AGENT' ")
    Integer countAgent();
}
