package com.devocean.Balbalm.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devocean.Balbalm.notification.domain.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findAllByToUserId(String userId);
}
