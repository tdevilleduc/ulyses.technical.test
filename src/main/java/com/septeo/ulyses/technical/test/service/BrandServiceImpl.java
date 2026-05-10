package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.repository.BrandRepository;
import com.septeo.ulyses.technical.test.util.CustomCache;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public CustomCache<Long, Brand> brandCache;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Brand> getAllBrands() {
        return brandCache.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Brand> getBrandById(Long id) {
        return Optional.ofNullable(brandCache.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Brand saveBrand(Brand brand) {
        return brandCache.refreshNowWith(() -> brandRepository.save(brand)); 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBrand(Long id) {
        brandCache.evictWith(id, () -> brandRepository.deleteById(id));
    }
}
