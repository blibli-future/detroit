package com.blibli.future.detroit.repository;

import com.blibli.future.detroit.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreSummaryRepository extends JpaRepository<ScoreSummary, Long> {
    List<ScoreSummary> findByCutOffHistory(CutOffHistory cutOffHistory);

    @Query(nativeQuery = true,
        value = "SELECT * FROM score_summary JOIN detroit_users ON score_summary.agent_id = detroit_users.id WHERE (score_summary.score_type=5) AND (detroit_users.agent_channel_id=:channel) AND (detroit_users.agent_position_id=:position) ORDER BY score LIMIT 5")
    List<User> topAgent(@Param("channel")Long channel, @Param("position")Long position);
}
