package com.septeo.ulyses.technical.test.config;

import java.time.Duration;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.repository.BrandRepository;
import com.septeo.ulyses.technical.test.util.CustomCache;

@Configuration
public class CacheConfig {
    @Autowired
    public BrandRepository brandRepository;
    
    @Bean
    public CustomCache<Long, Brand> brandCache() {
        return new CustomCache<Long, Brand>(() -> brandRepository.findAll().stream()
            .collect(Collectors.toMap(Brand::getId, Function.identity(), (a, b) -> a)),
            Duration.ofHours(1));
    }
}
