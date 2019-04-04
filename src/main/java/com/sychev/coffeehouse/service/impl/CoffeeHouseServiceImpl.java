package com.sychev.coffeehouse.service.impl;

import com.sychev.coffeehouse.exception.NotFoundCafeException;
import com.sychev.coffeehouse.model.entity.CafeEntity;
import com.sychev.coffeehouse.repository.CafeRepository;
import com.sychev.coffeehouse.service.CoffeeHouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CoffeeHouseServiceImpl implements CoffeeHouseService {

    private static final Logger logger = LoggerFactory.getLogger(CoffeeHouseServiceImpl.class);

    private final CafeRepository cafeRepository;

    public CoffeeHouseServiceImpl(
            CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    //todo проверить диапазон широт и высот
    @Override
    public Page<CafeEntity> getAllCafesAroundClient(Pageable pageable, Double latitude, Double longitude) {
        return cafeRepository.findByLatitudeBetweenAndLongitudeBetween(
                pageable,
                latitude - 20,
                latitude + 20,
                longitude - 20,
                latitude + 20);
    }

    @Override
    public Page<CafeEntity> getAllCafes(Pageable pageable) {
        return cafeRepository.findAll(pageable);
    }

    @Override
    public CafeEntity getCafeByUid(UUID cafeUid) {
        return cafeRepository.findByCafeUid(cafeUid).orElseThrow(() -> {
            logger.info("Not found cafe with uid={}", cafeUid);
            return new NotFoundCafeException("Not found cafe with uid=" + cafeUid);
        });
    }

    @Override
    public UUID addCafe(CafeEntity entity) {
        logger.debug("Add new cafe with name={}", entity.getName());
        return cafeRepository.save(entity).getCafeUid();
    }

    @Override
    public void updateCafe(CafeEntity entity) {
        CafeEntity cafe = cafeRepository.findByCafeUid(entity.getCafeUid()).orElseThrow(() -> {
            logger.info("Not found cafe with uid={}", entity.getCafeUid());
            return new NotFoundCafeException("Not found cafe with uid=" + entity.getCafeUid());
        });
        logger.debug("Update cafe with name={}", entity.getName());
        cafeRepository.save(cafe.copy(entity));
    }

    @Override
    @Transactional
    public void removeCafe(UUID cafeUid) {
        cafeRepository.deleteByCafeUid(cafeUid);
    }
}
