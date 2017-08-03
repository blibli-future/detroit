package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.AgentPosition;
import com.blibli.future.detroit.model.request.NewAgentPositionRequest;
import com.blibli.future.detroit.model.response.BaseRestListResponse;
import com.blibli.future.detroit.model.response.BaseRestResponse;
import com.blibli.future.detroit.service.AgentPositionService;
import com.blibli.future.detroit.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AgentPositionController {
    public static final String BASE_PATH = Constant.API_PATH_V1 + "/agent-positions";
    public static final String GET_ALL_AGENT_POSITION = BASE_PATH;
    public static final String GET_ONE_AGENT_POSITION = BASE_PATH + "/{agentPositionId}";
    public static final String CREATE_AGENT_POSITION = BASE_PATH;
    public static final String DELETE_AGENT_POSITION = BASE_PATH + "/{agentPositionId}";
    public static final String UPDATE_AGENT_POSITION = BASE_PATH + "/{agentPositionId}";

    @Autowired
    private AgentPositionService agentPositionService;

    @GetMapping(GET_ALL_AGENT_POSITION)
    public BaseRestListResponse<AgentPosition> getAllAgentPosition() {
        List<AgentPosition> allAgentPosition = agentPositionService.getAllAgentPosition();
        return new BaseRestListResponse<>(allAgentPosition);
    }

    @GetMapping(GET_ONE_AGENT_POSITION)
    public BaseRestResponse<AgentPosition> getOneAgentPosition(@PathVariable Long agentPositionId) {
        AgentPosition agentPosition = agentPositionService.getOneAgentPosition(agentPositionId);
        return new BaseRestResponse<>(agentPosition);
    }

    @PostMapping(CREATE_AGENT_POSITION)
    public BaseRestResponse createAgentPosition(@RequestBody NewAgentPositionRequest request) {
        AgentPosition returnAgentPosition = agentPositionService.createAgentPosition(request);
        if(returnAgentPosition != null) {
            return new BaseRestResponse();
        } else {
            return new BaseRestResponse(false, "Something happen :( ", "Error");
        }
    }

    @DeleteMapping(DELETE_AGENT_POSITION)
    public BaseRestResponse deleteAgentPosition(@PathVariable Long agentPositionId) {
        if(agentPositionService.deleteAgentPosition(agentPositionId)) {
            return new BaseRestResponse();
        }
        return new BaseRestResponse(false, "Something happen :( ", "Error");
    }

    @PatchMapping(UPDATE_AGENT_POSITION)
    public BaseRestResponse updateAgentPosition(@PathVariable Long agentPositionId, @RequestBody NewAgentPositionRequest request) {
        if(agentPositionService.updateAgentPosition(agentPositionId, request)) {
            return new BaseRestResponse();
        }
        return new BaseRestResponse(false, "Something happen :( ", "Error");
    }
}
