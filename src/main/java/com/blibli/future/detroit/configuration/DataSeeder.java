package com.blibli.future.detroit.configuration;

import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.UserRole;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.repository.UserRepository;
import com.blibli.future.detroit.repository.UserRoleRepository;
import com.blibli.future.detroit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements ApplicationRunner {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public DataSeeder(UserRepository userRepository,
                      UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public void run(ApplicationArguments args) {
        User agent = new User();
        agent.setEmail("agent@example.com");
        agent.setNickname("agent");
        agent.setFullname("Agent Nomor 1");
        agent.setGender("Male");
        agent.setPassword("secret");
        agent.setChannel("social media");
        agent.setUserType(UserType.AGENT);
        userRepository.save(agent);

        UserRole agentRole = new UserRole(
            agent.getEmail(), agent.getUserType().toString());
        userRoleRepository.save(agentRole);
    }
}
