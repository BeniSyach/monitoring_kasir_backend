package com.beni.syncapp.service;

import com.beni.syncapp.dto.ObjekPajakDTO;
import com.beni.syncapp.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ActivityRepository repo;

    public Object getData(
            String startDate,
            String endDate
    ) {

        LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate).atTime(23, 59, 59);

        List<Object[]> raw = repo.findDashboard(start, end);

        List<ObjekPajakDTO> result = raw.stream()
                .map(row -> new ObjekPajakDTO(
                        "NPWPD-" + row[0],
                        (String) row[1],
                        (String) row[2],
                        "UNKNOWN",
                        (String) row[4],
                        row[5] != null ? row[5].toString() : null,
                        (BigDecimal) row[6],
                        (String) row[3]
                ))
                .toList();

        return java.util.Map.of(
                "data", result
        );
    }
}