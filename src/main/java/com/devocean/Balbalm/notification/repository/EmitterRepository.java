package com.devocean.Balbalm.notification.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.devocean.Balbalm.notification.domain.entity.EmitterInfo;

@Repository
public class EmitterRepository {
    private final Map<String, EmitterInfo> emitters = new ConcurrentHashMap<>();

    public void save(String userId, EmitterInfo emitter) {
        emitters.put(userId, emitter);
    }

    public void deleteById(String userId) {
        emitters.remove(userId);
    }

    public EmitterInfo get(String userId) {
        return emitters.get(userId);
    }

    public Map<String, EmitterInfo> getAllSseEmitter() {
        return emitters;
    }

    public Set<String> findAllUserKey() {
        return emitters.keySet();
    }
}