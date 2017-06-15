package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.StatisticDiagram;
import com.blibli.future.detroit.model.StatisticInfo;
import com.blibli.future.detroit.repository.ReviewRepository;
import com.blibli.future.detroit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;

    public StatisticDiagram getCurrentAllStatisticDiagram() {
        List<StatisticDiagram> statisticDiagrams = new ArrayList<>();

        for (:
             ) {
            
        }
        
        return ;
    }
}
