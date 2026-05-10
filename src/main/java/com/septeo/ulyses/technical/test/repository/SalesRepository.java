package com.septeo.ulyses.technical.test.repository;

import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.VehicleSales;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Sales entity.
 */
@Repository
public interface SalesRepository {
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
     * @param page the page number to retrieve (0-based index)
     * @return a list of sales for the given brand and for the given page
     */
    List<Sales> findByBrandId(Long brandId, Integer page);

    /**
     * Find all sales for a given vehicle ID.
     * @param vehicleId the ID of the vehicle to find sales for
     * @param page the page number to retrieve (0-based index)
     * @return a list of sales for the given vehicle and for the given page
     */
    List<Sales> findByVehicleId(Long vehicleId, Integer page);

    /**
     * Find all sales with pagination. 
     * @param page the page number to retrieve (0-based index) (optional)
     * @return a list of sales for the given page
     */
    List<Sales> findByPage(Integer page);

    /**
     * Find the top 5 best sales
     * @param startDate the start date of the sales period to consider
     * @param endDate the end date of the sales period to consider
     * @return a list of the 5 best-selling vehicles for the given date range
     */
    List<VehicleSales> findBestSales(LocalDate startDate, LocalDate endDate);
}
