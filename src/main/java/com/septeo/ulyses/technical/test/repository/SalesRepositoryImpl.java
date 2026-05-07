package com.septeo.ulyses.technical.test.repository;

import com.septeo.ulyses.technical.test.entity.Sales;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

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

    @Override
    public List<Sales> findAll() {
        String stringQuery = "SELECT s FROM Sales s";
        TypedQuery<Sales> typedQuery = entityManager.createQuery(stringQuery, Sales.class);
        return typedQuery.getResultList();
    }

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
    public List<Sales> findByBrandId(Long brandId) {
        String stringQuery = "SELECT s FROM Sales s WHERE s.brand.id = :brandId";
        TypedQuery<Sales> typedQuery = entityManager.createQuery(stringQuery, Sales.class);
        typedQuery.setParameter("brandId", brandId);
        return typedQuery.getResultList();
    }

    @Override
    public List<Sales> findByVehicleId(Long vehicleId) {
        String stringQuery = "SELECT s FROM Sales s WHERE s.vehicle.id = :vehicleId";
        TypedQuery<Sales> typedQuery = entityManager.createQuery(stringQuery, Sales.class);
        typedQuery.setParameter("vehicleId", vehicleId);
        return typedQuery.getResultList();
    }
}
