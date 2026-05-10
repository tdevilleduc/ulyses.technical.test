package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.VehicleSales;
import com.septeo.ulyses.technical.test.repository.SalesRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    /**
     * {@inheritDoc}
     */
    @Override
    public List<VehicleSales> getBestSellingVehicles(LocalDate startDate, LocalDate endDate) {

        Map<Long, Long> salesPerVehicle = streamAllSales()
            .filter(v -> isInRange(v.getSaleDate(), startDate, endDate))
            .collect(Collectors.groupingBy(
                sales -> sales.getVehicle().getId(),
                Collectors.counting()
            ));

        return salesPerVehicle.entrySet().stream()
            .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
            .limit(5)
            .map(entry -> new VehicleSales(entry.getKey(), entry.getValue()))
            .toList();
    }

    private Stream<Sales> streamAllSales() {
        return Stream.iterate(0, page -> page + 1)
            .map(page -> salesRepository.findByPage(page))
            .takeWhile(list -> !list.isEmpty())
            .flatMap(List::stream);
}
    
    private boolean isInRange(LocalDate date, LocalDate start, LocalDate end) {
        return date != null
            && !date.isBefore(start)
            && !date.isAfter(end);
    }


    /**
     * {@inheritDoc}
     */
    public List<VehicleSales> getAnotherBestSellingVehicles(LocalDate startDate, LocalDate endDate) {
        return salesRepository.findBestSales(startDate, endDate);
    }
}
