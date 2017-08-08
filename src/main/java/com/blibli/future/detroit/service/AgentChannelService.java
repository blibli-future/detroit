package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.AgentChannel;
import com.blibli.future.detroit.model.request.NewAgentChannelRequest;
import com.blibli.future.detroit.repository.AgentChannelRepository;
import com.blibli.future.detroit.configuration.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentChannelService
{
    @Autowired
    private AgentChannelRepository agentChannelRepository;
    @Autowired
    Converter modelMapper;

    public List<AgentChannel> getAllAgentChannel() {
        return agentChannelRepository.findAll();
    }

    public AgentChannel getOneAgentChannel(Long agentChannelId) {
        return agentChannelRepository.findOne(agentChannelId);
    }

    public AgentChannel createAgentChannel(NewAgentChannelRequest request) {
        AgentChannel newAgentChannel = modelMapper.modelMapper()
            .map(request, AgentChannel.class);
        agentChannelRepository.saveAndFlush(newAgentChannel);
        return newAgentChannel;
    }

    public boolean deleteAgentChannel(Long agentChannelId) {
        agentChannelRepository.delete(agentChannelId);
        return true;
    }

    public boolean updateAgentChannel(Long agentChannelId, NewAgentChannelRequest request) {
        AgentChannel updatedAgentChannel = modelMapper.modelMapper()
            .map(request, AgentChannel.class);
        agentChannelRepository.save(updatedAgentChannel);
        return true;
    }
}
