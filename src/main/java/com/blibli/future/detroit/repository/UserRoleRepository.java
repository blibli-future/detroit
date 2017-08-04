package com.blibli.future.detroit.repository;


import com.blibli.future.detroit.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByEmail(String email);

    @Query("SELECT ur FROM UserRole ur WHERE ur.email = :email AND ur.role LIKE 'PARAM %'")
    List<UserRole> findReviewerRoleByEmail(@Param("email") String email);
}
