package com.blibli.future.detroit.repository;


import com.blibli.future.detroit.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByEmail(String email);
}
