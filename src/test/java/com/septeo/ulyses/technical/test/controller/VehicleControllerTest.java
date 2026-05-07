package com.septeo.ulyses.technical.test.controller;

import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.entity.Vehicle;
import com.septeo.ulyses.technical.test.service.BrandService;
import com.septeo.ulyses.technical.test.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = VehicleController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VehicleService vehicleService;

    @MockitoBean
    private BrandService brandService;

    @Test
    void testGetAllVehicles() throws Exception {
        when(vehicleService.getAllVehicles()).thenReturn(List.of());

        mockMvc.perform(get("/api/vehicles"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetVehicleById() throws Exception {
        Brand brand = new Brand(1L, "Renault", "French automobile manufacturer", List.of());
        Vehicle vehicle = new Vehicle(1L, brand, "Clio", "2022", "Red");
        when(vehicleService.getVehicleById(1L)).thenReturn(Optional.of(vehicle));

        mockMvc.perform(get("/api/vehicles/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetVehicleByIdNotFound() throws Exception {
        when(vehicleService.getVehicleById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/vehicles/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateVehicle() throws Exception {
        Brand brand = new Brand(1L, "Renault", "French automobile manufacturer", List.of());
        Vehicle vehicle = new Vehicle(1L, brand, "Clio", "2022", "Red");
        when(brandService.getBrandById(1L)).thenReturn(Optional.of(brand));
        when(vehicleService.saveVehicle(any())).thenReturn(vehicle);

        String vehicleJson = "{\"model\":\"Clio\",\"year\":\"2022\",\"color\":\"Red\",\"brand\":{\"id\":1}}";
        mockMvc.perform(post("/api/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vehicleJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateVehicleWithUnknownBrand() throws Exception {
        when(brandService.getBrandById(999L)).thenReturn(Optional.empty());

        String vehicleJson = "{\"model\":\"Clio\",\"year\":\"2022\",\"color\":\"Red\",\"brand\":{\"id\":999}}";
        mockMvc.perform(post("/api/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vehicleJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateVehicle() throws Exception {
        Brand brand = new Brand(1L, "Renault", "French automobile manufacturer", List.of());
        Vehicle vehicle = new Vehicle(1L, brand, "Updated Clio", "2023", "Blue");
        when(vehicleService.getVehicleById(1L)).thenReturn(Optional.of(vehicle));
        when(brandService.getBrandById(1L)).thenReturn(Optional.of(brand));
        when(vehicleService.saveVehicle(any())).thenReturn(vehicle);

        String vehicleJson = "{\"model\":\"Updated Clio\",\"year\":\"2023\",\"color\":\"Blue\",\"brand\":{\"id\":1}}";
        mockMvc.perform(put("/api/vehicles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vehicleJson))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateVehicleNotFound() throws Exception {
        when(vehicleService.getVehicleById(999L)).thenReturn(Optional.empty());

        String vehicleJson = "{\"model\":\"Updated Clio\",\"year\":\"2023\",\"color\":\"Blue\",\"brand\":{\"id\":1}}";
        mockMvc.perform(put("/api/vehicles/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vehicleJson))
                .andExpect(status().isNotFound());
    }
    @Test
    void testDeleteVehicle() throws Exception {
        Brand brand = new Brand(1L, "Renault", "French automobile manufacturer", List.of());
        Vehicle vehicle = new Vehicle(1L, brand, "Clio", "2022", "Red");
        when(vehicleService.getVehicleById(1L)).thenReturn(Optional.of(vehicle));

        mockMvc.perform(delete("/api/vehicles/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteVehicleNotFound() throws Exception {
        when(vehicleService.getVehicleById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/vehicles/999"))
                .andExpect(status().isNotFound());
    }
}
