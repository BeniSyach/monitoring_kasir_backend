package com.beni.syncapp.repository;

import com.beni.syncapp.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository
        extends JpaRepository<Activity, Long> {

    boolean existsBySourceId(String sourceId);
}