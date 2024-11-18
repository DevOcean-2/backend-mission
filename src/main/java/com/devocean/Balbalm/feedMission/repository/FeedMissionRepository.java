package com.devocean.Balbalm.feedMission.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devocean.Balbalm.feedMission.entity.FeedMission;

@Repository
public interface FeedMissionRepository extends JpaRepository<FeedMission, Long> {
	Optional<FeedMission> findById(Long id);
	Optional<FeedMission> findByMonth(int month);
}
