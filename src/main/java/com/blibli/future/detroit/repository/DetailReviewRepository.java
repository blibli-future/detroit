package com.blibli.future.detroit.repository;

import com.blibli.future.detroit.model.DetailReview;
import com.blibli.future.detroit.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailReviewRepository extends JpaRepository<DetailReview, Long> {
    List<DetailReview> findByReview(Review review);
}
