package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.VehicleSales;

import java.time.LocalDate;
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
     * @param page the page number for pagination (optional)
     * @return a list of sales for the given brand
     */
    List<Sales> getSalesByBrandId(Long brandId, Integer page);

    /**
     * Return all sales for a given vehicle.
     * @param vehicleId the ID of the vehicle to find sales for
     * @param page the page number for pagination (optional)
     * @return a list of sales for the given vehicle
     */
    List<Sales> getSalesByVehicleId(Long vehicleId, Integer page);

    /**
     * Return the top 5 best-selling vehicles based on the total quantity sold.
     * The method should consider sales within a specified date range.
     * @param startDate the start date of the sales period to consider
     * @param endDate the end date of the sales period to consider
     * @return a list of the 5 best-selling vehicles, ordered by quantity sold in descending order.
     */ 
    List<VehicleSales> getBestSellingVehicles(LocalDate startDate, LocalDate endDate);	

    /**
     * Return the top 5 best-selling vehicles based on the total quantity sold.
     * The method should consider sales within a specified date range.
     * 
     * This method is an alternative implementation to getBestSellingVehicles and should return the same results.
     * However, it should be implemented using a much better approach in terms of performance and efficiency, especially when dealing with large datasets.
     * @param startDate the start date of the sales period to consider
     * @param endDate the end date of the sales period to consider
     * @return a list of the 5 best-selling vehicles, ordered by quantity sold in descending order.
     */ 
    List<VehicleSales> getAnotherBestSellingVehicles(LocalDate startDate, LocalDate endDate);
}
