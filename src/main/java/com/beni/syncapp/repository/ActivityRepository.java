package com.beni.syncapp.repository;

import com.beni.syncapp.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

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

    @Query("""
    SELECT COALESCE(SUM(a.totalTransaksi), 0)
    FROM Activity a
    WHERE a.transDate BETWEEN :startDate AND :endDate
""")
    BigDecimal getTotalTransaksi(

            @Param("startDate")
            LocalDateTime startDate,

            @Param("endDate")
            LocalDateTime endDate
    );

    @Query(value = """
    SELECT
        TO_CHAR(trans_date, 'DD') as label,
        COALESCE(SUM(total_transaksi),0) as total
    FROM activity
    WHERE trans_date BETWEEN :startDate AND :endDate
    GROUP BY label
    ORDER BY label
""", nativeQuery = true)
    List<Object[]> getDailyChart(

            @Param("startDate")
            LocalDateTime startDate,

            @Param("endDate")
            LocalDateTime endDate
    );

    @Query(value = """
    SELECT
        TO_CHAR(trans_date, 'MM') as label,
        COALESCE(SUM(total_transaksi),0) as total
    FROM activity
    WHERE trans_date BETWEEN :startDate AND :endDate
    GROUP BY label
    ORDER BY label
""", nativeQuery = true)
    List<Object[]> getMonthlyChart(

            @Param("startDate")
            LocalDateTime startDate,

            @Param("endDate")
            LocalDateTime endDate
    );

    @Query("""
    SELECT a.store.id,
           a.store.name,
           a.store.alias,
           a.store.status,
           a.store.operationalStatus,
           FUNCTION('DATE', a.transDate),
           SUM(a.totalTransaksi)
    FROM Activity a
    WHERE a.transDate IS NOT NULL
    AND a.transDate BETWEEN :start AND :end
    GROUP BY a.store.id,
             a.store.name,
             a.store.alias,
             a.store.status,
             a.store.operationalStatus,
             FUNCTION('DATE', a.transDate)
    ORDER BY FUNCTION('DATE', a.transDate) ASC
""")
    List<Object[]> findDashboard(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}