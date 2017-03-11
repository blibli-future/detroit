package com.blibli.future.detroit.repository;


import com.blibli.future.detroit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
