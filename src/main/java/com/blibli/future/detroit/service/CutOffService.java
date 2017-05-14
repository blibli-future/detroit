package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.CutOffHistory;
import com.blibli.future.detroit.repository.CutOffRepository;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CutOffService {
    @Autowired
    private CutOffRepository cutOffRepository;

    public List<CutOffHistory> getAllCutOff() {
        return cutOffRepository.findAll();
    }

    public CutOffHistory getCurrentCutOff() {
        return cutOffRepository.findByEndIsNull();
    }
}
