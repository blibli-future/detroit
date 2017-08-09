package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.AgentChannel;
import com.blibli.future.detroit.model.AgentPosition;
import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.UserRole;
import com.blibli.future.detroit.model.dto.AgentDto;
import com.blibli.future.detroit.model.dto.ReviewerDto;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.model.request.NewUserRequest;
import com.blibli.future.detroit.repository.*;
import com.blibli.future.detroit.configuration.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private ParameterRepository parameterRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    Converter modelMapper;

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public List<User> getAllUser(UserType userType) {
        if (userType == null) {
            return getAllUser();
        }
        // Super admin is considered a reviewer too.
        if (userType == UserType.REVIEWER) {
            List<User> users = userRepository.findByUserType(UserType.REVIEWER);
            users.addAll(userRepository.findByUserType(UserType.SUPER_ADMIN));
            return users;
        }
        return userRepository.findByUserType(userType);
    }

    public User getOneUser(long userId) {
        return userRepository.findOne(userId);
    }

    public User createUser(NewUserRequest request) {
        User newUser = modelMapper.modelMapper()
            .map(request, User.class);
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(encryptedPassword);
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

    public User createAgent(AgentDto request) {
        User agent = modelMapper.modelMapper().map(request, User.class);
        agent.setUserType(UserType.AGENT);
        agent.setAgentPosition(
            agentPositionRepository.findByName(request.getAgentPosition()));
        for (AgentChannel channel: agent.getAgentPosition().getAgentChannels()) {
            if (channel.getName().equals(request.getAgentChannel())) {
                agent.setAgentChannel(channel);
                break;
            }
        }
        userRepository.saveAndFlush(agent);

        return agent;
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

        // Update super admin status
        if (request.getSuperAdmin() != reviewer.isSuperAdmin()) {
            userRoleRepository.delete(userRoleRepository.findByEmailOnlyType(reviewer.getEmail()));
            if(request.getSuperAdmin()) {
                reviewer.setUserType(UserType.SUPER_ADMIN);
                userRoleRepository.save(new UserRole(reviewer.getEmail(), UserType.SUPER_ADMIN.toString()));
            } else {
                reviewer.setUserType(UserType.REVIEWER);
                userRoleRepository.save(new UserRole(reviewer.getEmail(), UserType.REVIEWER.toString()));
            }
        }

        // Update reviewer role
        List<UserRole> oldRole = userRoleRepository.findReviewerRoleByEmail(reviewer.getEmail());
        LOG.info("Deleting reviewer %s roles: %s", reviewer.getEmail(), oldRole);
        userRoleRepository.delete(oldRole);

        for (String role: request.getReviewerRole()) {
            LOG.debug("Creating new role: %s", role);
            UserRole ur = new UserRole(reviewer, role);
            userRoleRepository.save(ur);
        }
        userRepository.saveAndFlush(reviewer);

        return reviewer;
    }

    public List<String> getParameterRoles() {
        return parameterRepository.getAllParameterNames();
    }

    public User createReviewer(ReviewerDto request) {
        User reviewer = modelMapper.modelMapper().map(request, User.class);

        for (String role: request.getReviewerRole()) {
            UserRole ur = new UserRole(reviewer, role);
            userRoleRepository.save(ur);
        }
        if (request.getSuperAdmin()) {
            reviewer.setUserType(UserType.SUPER_ADMIN);
        }
        userRoleRepository.save(new UserRole(reviewer.getEmail(), reviewer.getUserType().toString()));
        userRepository.saveAndFlush(reviewer);
        return reviewer;
    }
}
