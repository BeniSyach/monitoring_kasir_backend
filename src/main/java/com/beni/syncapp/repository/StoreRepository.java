package com.beni.syncapp.repository;

import com.beni.syncapp.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    long countByStatus(String status);

    long countByOperationalStatus(String operationalStatus);

    List<Store>
    findTop5ByOperationalStatusOrderByIdDesc(
            String operationalStatus
    );
}