package com.blibli.future.detroit.repository;

import com.blibli.future.detroit.model.AgentChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentChannelRepository extends JpaRepository<AgentChannel, Long> {
    AgentChannel findByName(String name);
}
