package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.CutOffHistory;
import com.blibli.future.detroit.model.response.CutOffResponse;
import com.blibli.future.detroit.repository.CutOffRepository;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CutOffService {
    @Autowired
    private CutOffRepository cutOffRepository;

    public List<CutOffResponse> getAllCutOff() {
        List<CutOffResponse> cutOffResponses = new ArrayList<>();
        for(CutOffHistory cutOffHistory : cutOffRepository.findAll()) {
            cutOffResponses.add(new CutOffResponse(cutOffHistory));
        }

        return cutOffResponses;
    }

    public CutOffResponse getCurrentCutOff() {
        CutOffHistory cutOffHistory = cutOffRepository.findByEndIsNull();
        return new CutOffResponse(cutOffHistory);
    }
}
