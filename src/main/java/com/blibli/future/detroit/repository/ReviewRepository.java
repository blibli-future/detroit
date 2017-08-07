package com.blibli.future.detroit.repository;

import com.blibli.future.detroit.model.CutOffHistory;
import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.Review;
import com.blibli.future.detroit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewerId(Long reviewerId);
    List<Review> findByAgentId(Long agentId);
    List<Review> findByAgentAndParameter(User agentId, Parameter parameterId);

    @Query("select count(*) from Review r where (r.reviewer=:reviewerId) and (r.cutOffHistory=:cutOffId) and (r.parameter=:parameterId)")
    Integer countByReviewer(@Param("reviewerId") User reviewerId, @Param("cutOffId") CutOffHistory cutOffId, @Param("parameterId") Parameter parameterId);

    List<Review> findByAgentAndCutOffHistory(User agent, CutOffHistory cutOffHistory);

    List<Review> findByAgentAndCutOffHistoryAndParameter(User agent, CutOffHistory cutOffHistory, Parameter parameter);

    List<Review> findByReviewerAndCutOffHistory(User reviewer, CutOffHistory cutOffHistory);
}
