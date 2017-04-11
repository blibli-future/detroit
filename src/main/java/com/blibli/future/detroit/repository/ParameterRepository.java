package com.blibli.future.detroit.repository;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {
}
