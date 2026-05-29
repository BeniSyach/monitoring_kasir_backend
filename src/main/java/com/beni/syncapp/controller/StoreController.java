package com.beni.syncapp.controller;

import com.beni.syncapp.dto.OfflineStoreResponse;
import com.beni.syncapp.dto.StoreStatsResponse;
import com.beni.syncapp.entity.Store;
import com.beni.syncapp.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreRepository storeRepository;

    @GetMapping
    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Store getById(@PathVariable Long id) {
        return storeRepository.findById(id)
                .orElseThrow();
    }

    @PostMapping
    public Store create(@RequestBody Store request) {
        return storeRepository.save(request);
    }

    @PutMapping("/{id}")
    public Store update(
            @PathVariable Long id,
            @RequestBody Store request
    ) {

        Store store = storeRepository.findById(id)
                .orElseThrow();

        store.setName(request.getName());
        store.setAlias(request.getAlias());

        return storeRepository.save(store);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        storeRepository.deleteById(id);

        return "Store berhasil dihapus";
    }

    @GetMapping("/stats")
    public StoreStatsResponse getStats() {

        long totalStore =
                storeRepository.count();

        long storeAktif =
                storeRepository.countByStatus("ACTIVE");

        long storeTidakAktif =
                storeRepository.countByStatus("INACTIVE");

        long offline =
                storeRepository.countByOperationalStatus("OFFLINE");

        long tutup =
                storeRepository.countByOperationalStatus("TUTUP");

        return new StoreStatsResponse(
                totalStore,
                storeAktif,
                storeTidakAktif,
                offline,
                tutup
        );
    }

    @GetMapping("/offline/latest")
    public List<OfflineStoreResponse> getOfflineLatest() {

        List<Store> stores =
                storeRepository
                        .findTop5ByOperationalStatusOrderByIdDesc(
                                "OFFLINE"
                        );

        return stores.stream()
                .map(store -> new OfflineStoreResponse(
                        store.getName(),
                        store.getLastSyncAt() != null
                                ? store.getLastSyncAt().toString()
                                : "-"
                ))
                .toList();
    }
}