package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the SalesService interface.
 * This class provides the implementation for all sales-related operations.
 */
@Service
@Transactional(readOnly = false)
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sales> getSalesByPage(Integer page) {
        if (page == null || page < 0) {
            return salesRepository.findByPage(0);
        }
        return salesRepository.findByPage(page - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Sales> getSalesById(Long id) {
        return salesRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sales> getSalesByBrandId(Long brandId, Integer page) {
        if (page == null || page < 0) {
            return salesRepository.findByBrandId(brandId, 0);
        }
        return salesRepository.findByBrandId(brandId, page - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sales> getSalesByVehicleId(Long vehicleId, Integer page) {
        if (page == null || page < 0) {
            return salesRepository.findByVehicleId(vehicleId,0);
        }
        return salesRepository.findByVehicleId(vehicleId, page - 1);
    }
}
