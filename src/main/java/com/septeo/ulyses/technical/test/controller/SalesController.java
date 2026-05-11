package com.septeo.ulyses.technical.test.controller;

import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.VehicleSales;
import com.septeo.ulyses.technical.test.service.SalesService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    private static final Logger logger = LoggerFactory.getLogger(SalesController.class);

    @Autowired
    private SalesService salesService;

    @GetMapping
    public ResponseEntity<List<Sales>> getSalesByPage(
        @RequestParam(value = "page", required = false) Integer page
    ) {
        return ResponseEntity.ok(salesService.getSalesByPage(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sales> getSalesById(@PathVariable Long id) {
        return salesService.getSalesById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<List<Sales>> getSalesByBrandId(
        @PathVariable Long brandId,
        @RequestParam(value = "page", required = false) Integer page
    ) {
        return ResponseEntity.ok(salesService.getSalesByBrandId(brandId, page));
    }

    @GetMapping("/vehicles/{vehicleId}")
    public ResponseEntity<List<Sales>> getSalesByVehicleId(
        @PathVariable Long vehicleId,
        @RequestParam(value = "page", required = false) Integer page
    ) {
        return ResponseEntity.ok(salesService.getSalesByVehicleId(vehicleId, page));
    }

    @GetMapping("/vehicles/bestSelling")
    public ResponseEntity<List<VehicleSales>> getBestSellingVehicles(
        @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateParam,
        @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDateParam
    ) {

        LocalDate startDate = (startDateParam != null) ? startDateParam : LocalDate.of(1970, 1, 1); // 1 Jan 1970
        LocalDate endDate = (endDateParam != null) ? endDateParam : LocalDate.now(); // Current date

        if (startDate.isAfter(endDate)) {
            logger.error("endDate {} must be after startDate {}", endDate, startDate);
            return ResponseEntity.badRequest().build();
        }

        if (startDate.isAfter(LocalDate.now())) {
            logger.error("startDate {} cannot be in the future", startDate);
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(salesService.getBestSellingVehicles(startDate, endDate));
    }

    @GetMapping("/vehicles/anotherBestSelling")
    public ResponseEntity<List<VehicleSales>> getAnotherBestSellingVehicles(
        @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateParam,
        @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDateParam
    ) {

        LocalDate startDate = (startDateParam != null) ? startDateParam : LocalDate.of(1970, 1, 1); // 1 Jan 1970
        LocalDate endDate = (endDateParam != null) ? endDateParam : LocalDate.now(); // Current date

        if (startDate.isAfter(endDate)) {
            logger.error("endDate {} must be after startDate {}", endDate, startDate);
            return ResponseEntity.badRequest().build();
        }

        if (startDate.isAfter(LocalDate.now())) {
            logger.error("startDate {} cannot be in the future", startDate);
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(salesService.getAnotherBestSellingVehicles(startDate, endDate));
    }
}
