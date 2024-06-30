package com.laundry.customer_service.repository;

import com.laundry.customer_service.entity.Rating;
import com.laundry.customer_service.entity.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, RatingId> {
}
