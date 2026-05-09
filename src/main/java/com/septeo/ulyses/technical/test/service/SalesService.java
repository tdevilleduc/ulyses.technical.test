package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.Sales;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Sales operations.
 */
public interface SalesService {

    /**
     * Get all sales with pagination.
     *
     * @param page the page number for pagination (optional)
     * @return a list of all sales, paginated with 10 sales per page.
     *         If page is provided, return the corresponding page of sales. 
     *         If page is unset or less than 0, return the first page.
     */
    List<Sales> getSalesByPage(Integer page);

    /**
     * Get a sales by its ID.
     *
     * @param id the ID of the sales to find
     * @return an Optional containing the sales if found, or empty if not found
     */
    Optional<Sales> getSalesById(Long id);

    /**
     * Return all sales for a given brand.
     * @param brandId the ID of the brand to find sales for
     * @return a list of sales for the given brand
     */
    List<Sales> getSalesByBrandId(Long brandId);

    /**
     * Return all sales for a given vehicle.
     * @param vehicleId the ID of the vehicle to find sales for
     * @return a list of sales for the given vehicle
     */
    List<Sales> getSalesByVehicleId(Long vehicleId);

}
