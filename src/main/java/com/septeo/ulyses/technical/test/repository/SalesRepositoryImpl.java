package com.septeo.ulyses.technical.test.repository;

import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.VehicleSales;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the SalesRepository interface.
 * This class provides the implementation for all sales-related operations.
 */
@Repository
public class SalesRepositoryImpl implements SalesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${sales.page.size:10}")
    private int pageSize;

    @Override
    public Optional<Sales> findById(Long id) {
        String stringQuery = "SELECT s FROM Sales s WHERE s.id = :id";
        TypedQuery<Sales> typedQuery = entityManager.createQuery(stringQuery, Sales.class);
        typedQuery.setParameter("id", id);

        try {
            return Optional.of(typedQuery.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Sales> findByBrandId(Long brandId, Integer page) {
        String stringQuery = "SELECT s FROM Sales s WHERE s.brand.id = :brandId";
        TypedQuery<Sales> typedQuery = createQueryWithPagination(stringQuery, page);
        typedQuery.setParameter("brandId", brandId);
        return typedQuery.getResultList();
    }

    @Override
    public List<Sales> findByVehicleId(Long vehicleId, Integer page) {
        String stringQuery = "SELECT s FROM Sales s WHERE s.vehicle.id = :vehicleId";
        TypedQuery<Sales> typedQuery = createQueryWithPagination(stringQuery, page);
        typedQuery.setParameter("vehicleId", vehicleId);
        return typedQuery.getResultList();
    }

    @Override
    public List<Sales> findByPage(Integer page) {
        String stringQuery = "SELECT s FROM Sales s";
        TypedQuery<Sales> typedQuery = createQueryWithPagination(stringQuery, page);
        return typedQuery.getResultList();
    }

    /**
     * add pagination to the query, only if page is not null and greater than 0.
     * @param stringQuery the SQL query string
     * @param page the page number for pagination (0-based index) (optional)
     * @return the paginated query
     * @throws IllegalArgumentException if page is less than 0
     */
    private TypedQuery<Sales> createQueryWithPagination (String stringQuery, Integer page) throws IllegalArgumentException {
        if (page == null || page < 0) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 0");
        }
        TypedQuery<Sales> typedQuery = entityManager.createQuery(stringQuery, Sales.class);
        typedQuery.setFirstResult(page * pageSize);
        typedQuery.setMaxResults(pageSize);
        return typedQuery;
    }

    @Override
    public List<VehicleSales> findBestSales(LocalDate startDate, LocalDate endDate) {
        String stringQuery = "SELECT new com.septeo.ulyses.technical.test.entity.VehicleSales(s.vehicle.id, COUNT(s)) " +
                "FROM Sales s " +
                "WHERE s.saleDate >= :startDate AND s.saleDate <= :endDate " +
                "GROUP BY s.vehicle.id " +
                "ORDER BY COUNT(s) DESC";
        TypedQuery<VehicleSales> typedQuery = entityManager.createQuery(stringQuery, VehicleSales.class);
        typedQuery.setMaxResults(5);
        typedQuery.setParameter("startDate", startDate);
        typedQuery.setParameter("endDate", endDate);
        return typedQuery.getResultList();
    }
}
