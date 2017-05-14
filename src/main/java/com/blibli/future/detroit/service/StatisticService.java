package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.StatisticInfo;
import com.blibli.future.detroit.repository.ReviewRepository;
import com.blibli.future.detroit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;


}
