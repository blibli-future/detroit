package com.blibli.future.detroit.repository;

import com.blibli.future.detroit.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreSummaryRepository extends JpaRepository<ScoreSummary, Long> {
    List<ScoreSummary> findByCutOffHistory(CutOffHistory cutOffHistory);

    @Query("SELECT s FROM ScoreSummary s " +
        "JOIN s.agent a " +
        "WHERE a.agentChannel=:channel " +
        "AND a.agentPosition=:position " +
        "ORDER BY s.score DESC")
    List<ScoreSummary> topAgent(@Param("channel")AgentChannel channel, @Param("position")AgentPosition position);
}
