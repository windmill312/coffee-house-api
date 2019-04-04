package com.sychev.coffeehouse.service;

import com.sychev.coffeehouse.model.entity.CafeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CoffeeHouseService {

    Page<CafeEntity> getAllCafesAroundClient(Pageable pageable, Double latitude, Double longitude);

    Page<CafeEntity> getAllCafes(Pageable pageable);

    CafeEntity getCafeByUid(UUID cafeUid);

    UUID addCafe(CafeEntity entity);

    void updateCafe(CafeEntity entity);

    void removeCafe(UUID cafeUid);

}
