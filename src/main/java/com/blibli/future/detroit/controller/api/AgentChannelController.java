package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.AgentChannel;
import com.blibli.future.detroit.model.request.NewAgentChannelRequest;
import com.blibli.future.detroit.model.response.BaseRestListResponse;
import com.blibli.future.detroit.model.response.BaseRestResponse;
import com.blibli.future.detroit.service.AgentChannelService;
import com.blibli.future.detroit.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.List;

@RestController
public class AgentChannelController {
    public static final String BASE_PATH = Constant.API_PATH_V1 + "/agent-channels";
    public static final String GET_ALL_AGENT_CHANNEL = BASE_PATH;
    public static final String GET_ONE_AGENT_CHANNEL = BASE_PATH + "/{agentChannelId}";
    public static final String CREATE_AGENT_CHANNEL = BASE_PATH;
    public static final String DELETE_AGENT_CHANNEL = BASE_PATH + "/{agentChannelId}";
    public static final String UPDATE_AGENT_CHANNEL = BASE_PATH + "/{agentChannelId}";

    @Autowired
    private AgentChannelService agentChannelService;

    @GetMapping(GET_ALL_AGENT_CHANNEL)
    public BaseRestListResponse<AgentChannel> getAllAgentChannel() {
        List<AgentChannel> allAgentChannel = agentChannelService.getAllAgentChannel();
        return new BaseRestListResponse<>(allAgentChannel);
    }

    @GetMapping(GET_ONE_AGENT_CHANNEL)
    public BaseRestResponse<AgentChannel> getOneAgentChannel(@PathVariable Long agentChannelId) {
        AgentChannel agentChannel = agentChannelService.getOneAgentChannel(agentChannelId);
        return new BaseRestResponse<>(agentChannel);
    }

    @PostMapping(CREATE_AGENT_CHANNEL)
    public BaseRestResponse createAgentChannel(@RequestBody NewAgentChannelRequest request) {
        AgentChannel returnAgentChannel = agentChannelService.createAgentChannel(request);
        if(returnAgentChannel != null) {
            return new BaseRestResponse();
        } else {
            return new BaseRestResponse(false, "Something happen :( ", "Error");
        }
    }

    @DeleteMapping(DELETE_AGENT_CHANNEL)
    public BaseRestResponse<AgentChannel> deleteAgentChannel(@PathVariable Long agentChannelId) {
        if(agentChannelService.deleteAgentChannel(agentChannelId)) {
            return new BaseRestResponse();
        }
        return new BaseRestResponse(false, "Something happen :( ", "Error");
    }

    @PatchMapping(UPDATE_AGENT_CHANNEL)
    public BaseRestResponse updateAgentChannel(@PathVariable Long agentChannelId, @RequestBody NewAgentChannelRequest request) {
        if(agentChannelService.updateAgentChannel(agentChannelId, request)) {
            return new BaseRestResponse();
        }
        return new BaseRestResponse(false, "Something happen :( ", "Error");
    }
}
