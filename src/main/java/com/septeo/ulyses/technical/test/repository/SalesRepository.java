package com.septeo.ulyses.technical.test.repository;

import com.septeo.ulyses.technical.test.entity.Sales;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Sales entity.
 */
@Repository
public interface SalesRepository {
    /**
     * Find all sales.
     *
     * @return a list of all sales
     */
    List<Sales> findAll();

    /**
     * Find a sale by its ID.
     *
     * @param id the ID of the sale to find
     * @return an Optional containing the sale if found, or empty if not found
     */
    Optional<Sales> findById(Long id);

    /**
     * Find all sales for a given brand ID.
     * @param brandId the ID of the brand to find sales for
     * @return a list of sales for the given brand
     */
    List<Sales> findByBrandId(Long brandId);

    /**
     * Find all sales for a given vehicle ID.
     * @param vehicleId the ID of the vehicle to find sales for
     * @return a list of sales for the given vehicle
     */
    List<Sales> findByVehicleId(Long vehicleId);

}
