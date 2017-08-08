package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.AgentPosition;
import com.blibli.future.detroit.model.request.NewAgentPositionRequest;
import com.blibli.future.detroit.repository.AgentPositionRepository;
import com.blibli.future.detroit.util.configuration.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentPositionService {
    @Autowired
    private AgentPositionRepository agentPositionRepository;
    @Autowired
    Converter modelMapper;

    public List<AgentPosition> getAllAgentPosition() {
        return agentPositionRepository.findAll();
    }

    public AgentPosition getOneAgentPosition(Long agentPositionId) {
        return agentPositionRepository.findOne(agentPositionId);
    }

    public AgentPosition createAgentPosition(NewAgentPositionRequest request) {
        AgentPosition newAgentPosition = new AgentPosition();
        newAgentPosition.setName(request.getName());
        agentPositionRepository.saveAndFlush(newAgentPosition);
        return newAgentPosition;
    }

    public boolean deleteAgentPosition(Long agentPositionId) {
        agentPositionRepository.delete(agentPositionId);
        return true;
    }

    public boolean updateAgentPosition(Long agentPositionId, NewAgentPositionRequest request) {
        AgentPosition updatedAgentPosition = modelMapper.modelMapper()
            .map(request, AgentPosition.class);
        agentPositionRepository.save(updatedAgentPosition);
        return true;
    }
}
