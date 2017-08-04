package com.blibli.future.detroit.repository;

import com.blibli.future.detroit.model.AgentChannel;
import com.blibli.future.detroit.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    List<Parameter> findByAgentChannel(AgentChannel agentChannel);
    Parameter findByNameAndAgentChannel(String name, AgentChannel channel);


    @Query("SELECT p.name FROM Parameter p")
    List<String> getAllParameterNames();
}
