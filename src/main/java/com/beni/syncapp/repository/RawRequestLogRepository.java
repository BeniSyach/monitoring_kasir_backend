package com.beni.syncapp.repository;

import com.beni.syncapp.entity.RawRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawRequestLogRepository
        extends JpaRepository<RawRequestLog, Long> {
}