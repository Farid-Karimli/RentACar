package com.example.sampleproject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    /* Find the reviews under a specific vehicle */
    List<Review> findAllByVehicleID(Long id);
}
