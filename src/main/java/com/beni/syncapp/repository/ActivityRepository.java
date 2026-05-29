package com.beni.syncapp.repository;

import com.beni.syncapp.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository
        extends JpaRepository<Activity, Long> {

    boolean existsBySourceId(String sourceId);

    // =========================
    // TOP TRANSAKSI
    // =========================
    @Query(value = """
        SELECT
            s.name AS name,
            SUM(a.total_transaksi) AS total
        FROM activity a
        JOIN store s ON s.id = a.store_id
        GROUP BY s.id, s.name
        ORDER BY total DESC
        LIMIT 5
    """, nativeQuery = true)
    List<Object[]> getTopTransactionStores();

    // =========================
    // TOP PAJAK
    // contoh pajak 10%
    // =========================
    @Query(value = """
        SELECT
            s.name AS name,
            SUM(a.total_transaksi * 0.1) AS total
        FROM activity a
        JOIN store s ON s.id = a.store_id
        GROUP BY s.id, s.name
        ORDER BY total DESC
        LIMIT 5
    """, nativeQuery = true)
    List<Object[]> getTopTaxStores();

    // =========================
    // 100 TRANSAKSI TERAKHIR
    // =========================
    @Query(value = """
        SELECT
            s.name,
            (a.total_transaksi * 0.1) AS pajak,
            a.total_transaksi
        FROM activity a
        JOIN store s ON s.id = a.store_id
        ORDER BY a.id DESC
        LIMIT 100
    """, nativeQuery = true)
    List<Object[]> getLastTransactions();
}