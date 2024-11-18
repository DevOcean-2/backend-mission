package com.devocean.Balbalm.mission.service;

import org.springframework.stereotype.Service;

import com.devocean.Balbalm.mission.dataprovider.MissionDataProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MissionService {
	private final MissionDataProvider missionDataProvider;
}
