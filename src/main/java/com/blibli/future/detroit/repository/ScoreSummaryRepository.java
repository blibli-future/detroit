package com.blibli.future.detroit.repository;

import com.blibli.future.detroit.model.CutOffHistory;
import com.blibli.future.detroit.model.ScoreSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreSummaryRepository extends JpaRepository<ScoreSummary, Long> {
    List<ScoreSummary> findByCutOffHistory(CutOffHistory cutOffHistory);
}
