package com.blibli.future.detroit.repository;

import com.blibli.future.detroit.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParameterRepository extends JpaRepository<Category, Long> {
}
