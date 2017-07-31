package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.UserRole;
import com.blibli.future.detroit.model.dto.AgentDto;
import com.blibli.future.detroit.model.dto.ReviewerDto;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.model.request.NewUserRequest;
import com.blibli.future.detroit.repository.AgentChannelRepository;
import com.blibli.future.detroit.repository.AgentPositionRepository;
import com.blibli.future.detroit.repository.UserRepository;
import com.blibli.future.detroit.repository.UserRoleRepository;
import com.blibli.future.detroit.util.configuration.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private AgentChannelRepository agentChannelRepository;

    @Autowired
    private AgentPositionRepository agentPositionRepository;

    @Autowired
    Converter modelMapper;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public List<User> getAllUser(UserType userType) {
        if (userType == null) {
            return getAllUser();
        }
        return userRepository.findByUserType(userType);
    }

    public User getOneUser(long userId) {
        return userRepository.findOne(userId);
    }

    public User createUser(NewUserRequest request) {
        User newUser = modelMapper.modelMapper()
            .map(request, User.class);
        newUser = userRepository.saveAndFlush(newUser);

        UserRole role = new UserRole();
        role.setEmail(newUser.getEmail());
        role.setRole(newUser.getUserType().toString());
        userRoleRepository.save(role);

        return newUser;
    }

    public boolean deleteUser(Long userId) {
        userRepository.delete(userId);
        return true;
    }

    public boolean updateUser(Long userId, NewUserRequest request) {
        // TODO throw error if userId doesn't exist
        User user = modelMapper.modelMapper()
            .map(request, User.class);
        userRepository.save(user);

        return true;
    }

    public User updateAgent(Long agentId, AgentDto request) {
        User agent = userRepository.getOne(request.getId());
        modelMapper.modelMapper().map(request, agent);
        agent.setAgentChannel(
            agentChannelRepository.findByName(request.getAgentChannel()));
        agent.setAgentPosition(
            agentPositionRepository.findByName(request.getAgentPosition()));
        userRepository.saveAndFlush(agent);

        return agent;
    }

    public User updateReviewer(ReviewerDto request) {
        User reviewer = userRepository.getOne(request.getId());
        modelMapper.modelMapper().map(request, reviewer);
        userRepository.saveAndFlush(reviewer);

        return reviewer;
    }
}
