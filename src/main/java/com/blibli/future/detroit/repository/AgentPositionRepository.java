package com.blibli.future.detroit.repository;

import com.blibli.future.detroit.model.AgentPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentPositionRepository extends JpaRepository<AgentPosition, Long> {
    AgentPosition findByName(String name);
}
