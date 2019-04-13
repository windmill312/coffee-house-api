package com.github.windmill312.coffeehouse.repository;

import com.github.windmill312.coffeehouse.model.entity.CafeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CafeRepository extends JpaRepository<CafeEntity, Integer> {

    Optional<CafeEntity> findByName(String name);

    Optional<CafeEntity> findByCafeUid(UUID cafeUid);

    void deleteByCafeUid(UUID cafeUid);

    Page<CafeEntity> findByLatitudeBetweenAndLongitudeBetween(Pageable pageable,
                                                              Double fromLatitude,
                                                              Double tillLatitude,
                                                              Double fromLongitude,
                                                              Double tillLongitude);
}
