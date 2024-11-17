package com.devocean.Balbalm.feedMission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devocean.Balbalm.feedMission.entity.FeedMissionUser;

@Repository
public interface FeedMissionUserRepository extends JpaRepository<FeedMissionUser, Long> {
}
