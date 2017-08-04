package com.blibli.future.detroit.repository;

import com.blibli.future.detroit.model.CutOffHistory;
import com.blibli.future.detroit.model.ScoreSummary;
import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.enums.ScoreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreSummaryRepository extends JpaRepository<ScoreSummary, Long> {
    List<ScoreSummary> findByCutOffHistory(CutOffHistory cutOffHistory);
    List<ScoreSummary> findByCutOffHistoryAndAgent(CutOffHistory cutOffHistory, User agent);
    List<ScoreSummary> findByCutOffHistoryAndFkId(CutOffHistory cutOffHistory, Long fkId);
    List<ScoreSummary> findByCutOffHistoryAndAgentAndScoreType(CutOffHistory cutOffHistory, User agent, ScoreType scoreType);
}
