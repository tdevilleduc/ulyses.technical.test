package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.repository.BrandRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the BrandService interface.
 * This class provides the implementation for all brand-related operations.
 */
@Service
@Transactional(readOnly = false)
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(value = "brands")
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(value = "brands", key = "#id")
    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict(value = "brands", key = "#id")
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict(value = "brands", key = "#id")
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
}
