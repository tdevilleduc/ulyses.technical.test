package com.septeo.ulyses.technical.test.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleSales {
    
    private Long vehicleId; 
    private Long quantitySold;
    
    // Constructor to initialize the fields when creating a new instance of VehicleSales in SQL query
    public VehicleSales(Long vehicleId, Long quantitySold) {
        this.vehicleId = vehicleId;
        this.quantitySold = quantitySold;
    }
}
