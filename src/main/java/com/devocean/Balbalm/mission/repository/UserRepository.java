package com.devocean.Balbalm.mission.repository;

import com.devocean.Balbalm.mission.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByUserId(String userId);
}
