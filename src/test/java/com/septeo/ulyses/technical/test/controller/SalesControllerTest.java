package com.septeo.ulyses.technical.test.controller;

import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.Vehicle;
import com.septeo.ulyses.technical.test.service.SalesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = SalesController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class SalesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SalesService salesService;

    @Test
    void testGetSalesByPage() throws Exception {
        when(salesService.getSalesByPage(0)).thenReturn(List.of());

        mockMvc.perform(get("/api/sales"))
                .andExpect(status().isOk())
            ;
    }

    @Test
    void testGetSalesById() throws Exception {
        Brand brand = new Brand(1L, "Renault", "French automobile manufacturer", List.of());
        Vehicle vehicle = new Vehicle(1L, brand, "Clio", "2022", "Red");
        Sales sales = new Sales(1L, brand, vehicle, LocalDate.of(2025, 1, 1), new BigDecimal("14850.75"));
        when(salesService.getSalesById(1L)).thenReturn(Optional.of(sales));

        mockMvc.perform(get("/api/sales/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
            ;
    }

    @Test
    void testGetSalesByIdNotFound() throws Exception {
        when(salesService.getSalesById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/sales/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetSalesByBrandId() throws Exception {
        Brand brand = new Brand(1L, "Renault", "French automobile manufacturer", List.of());
        Vehicle vehicle = new Vehicle(1L, brand, "Clio", "2022", "Red");
        Sales sales = new Sales(1L, brand, vehicle, LocalDate.of(2025, 1, 1), new BigDecimal("14850.75"));
        when(salesService.getSalesByBrandId(1L, null)).thenReturn(List.of(sales));

        mockMvc.perform(get("/api/sales/brands/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
            ;
    }

    @Test
    void testGetSalesByBrandIdEmpty() throws Exception {
        when(salesService.getSalesByBrandId(999L, null)).thenReturn(List.of());

        mockMvc.perform(get("/api/sales/brands/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty())
            ;
    }

    @Test
    void testGetSalesByVehicleId() throws Exception {
        Brand brand = new Brand(1L, "Renault", "French automobile manufacturer", List.of());
        Vehicle vehicle = new Vehicle(1L, brand, "Clio", "2022", "Red");
        Sales sales = new Sales(1L, brand, vehicle, LocalDate.of(2025, 1, 1), new BigDecimal("14850.75"));
        when(salesService.getSalesByVehicleId(1L, null)).thenReturn(List.of(sales));

        mockMvc.perform(get("/api/sales/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
            ;
    }

    @Test
    void testGetSalesByVehicleIdEmpty() throws Exception {
        when(salesService.getSalesByVehicleId(999L, null)).thenReturn(List.of());

        mockMvc.perform(get("/api/sales/vehicles/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty())
            ;
    }
}
