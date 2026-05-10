package com.septeo.ulyses.technical.test.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.repository.BrandRepository;

@SpringBootTest
class BrandServiceCacheTest {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CacheManager cacheManager;

    @MockitoBean
    private BrandRepository brandRepository;

    @BeforeEach
    void clearCache() {
        Objects.requireNonNull(cacheManager.getCache("brands")).clear();
    }

    @Test
    void getAllBrands_shouldHitCacheOnSecondCall() {
        when(brandRepository.findAll()).thenReturn(List.of(new Brand()));

        brandService.getAllBrands();
        brandService.getAllBrands();

        // only the first call should hit the repository, the second should hit the cache
        verify(brandRepository, times(1)).findAll();
    }

    @Test
    void saveBrand_shouldEvictCache() {
        when(brandRepository.findAll()).thenReturn(List.of());
        when(brandRepository.save(any())).thenReturn(new Brand());

        brandService.getAllBrands();
        brandService.saveBrand(new Brand());
        brandService.getAllBrands();

        // the first call should hit the repository, the second should evict the cache and hit the repository again
        verify(brandRepository, times(2)).findAll();
    }
}

