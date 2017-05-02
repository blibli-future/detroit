package com.blibli.future.detroit.configuration;

import com.blibli.future.detroit.model.AgentChannel;
import com.blibli.future.detroit.model.AgentPosition;
import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.UserRole;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.repository.AgentChannelRepository;
import com.blibli.future.detroit.repository.AgentPositionRepository;
import com.blibli.future.detroit.repository.UserRepository;
import com.blibli.future.detroit.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements ApplicationRunner {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private  AgentChannelRepository agentChannelRepository;
    private AgentPositionRepository agentPositionRepository;

    @Autowired
    public DataSeeder(UserRepository userRepository,
                      UserRoleRepository userRoleRepository,
                      AgentChannelRepository agentChannelRepository,
                      AgentPositionRepository agentPositionRepository ) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.agentChannelRepository = agentChannelRepository;
        this.agentPositionRepository = agentPositionRepository;
    }


    public void run(ApplicationArguments args) {
        AgentChannel agentChannel = new AgentChannel();
        agentChannel.setName("Chat");
        agentChannelRepository.save(agentChannel);

        AgentPosition agentPosition = new AgentPosition();
        agentPosition.setName("Inbound");
        agentPositionRepository.save(agentPosition);

        // API KEY = YWdlbnRAZXhhbXBsZS5jb206c2VjcmV0
        User agent = new User();
        agent.setEmail("agent@example.com");
        agent.setNickname("agent");
        agent.setFullname("Agent Nomor 1");
        agent.setGender("Male");
        agent.setPassword("secret");
        agent.setUserType(UserType.AGENT);
        agent.setAgentChannel(agentChannel);
        agent.setAgentPosition(agentPosition);
        userRepository.save(agent);

        UserRole agentRole = new UserRole(
            agent.getEmail(), agent.getUserType().toString());
        userRoleRepository.save(agentRole);
    }
}
