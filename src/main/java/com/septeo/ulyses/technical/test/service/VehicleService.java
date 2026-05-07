package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.Vehicle;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Vehicle operations.
 */
public interface VehicleService {

    /**
     * Get all vehicles.
     *
     * @return a list of all vehicles
     */
    List<Vehicle> getAllVehicles();

    /**
     * Get a vehicle by its ID.
     *
     * @param id the ID of the vehicle to find
     * @return an Optional containing the vehicle if found, or empty if not found
     */
    Optional<Vehicle> getVehicleById(Long id);

    /**
     * Save a vehicle.
     *
     * @param vehicle the vehicle to save
     * @return the saved vehicle
     */
    Vehicle saveVehicle(Vehicle vehicle);

    /**
     * Delete a vehicle by its ID.
     *
     * @param id the ID of the vehicle to delete
     */
    void deleteVehicle(Long id);
}
