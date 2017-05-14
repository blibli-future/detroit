package com.blibli.future.detroit.repository;

import com.blibli.future.detroit.model.CutOffHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CutOffRepository extends JpaRepository<CutOffHistory, Long> {
    CutOffHistory findByEndIsNull();
}
