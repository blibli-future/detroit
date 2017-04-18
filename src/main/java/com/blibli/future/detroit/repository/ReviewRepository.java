package com.blibli.future.detroit.repository;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Review;
import com.blibli.future.detroit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewerId(Long reviewerId);
    List<Review> findByAgentId(Long agentId);
    List<Review> findByAgentAndCategory(User agentId, Category categoryId);
}
