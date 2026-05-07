package com.septeo.ulyses.technical.test.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.service.BrandService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

@WebMvcTest(value = BrandController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BrandService brandService;


    @Test
    void testGetAllBrands() throws Exception {
        when(brandService.getAllBrands()).thenReturn(List.of());

        mockMvc.perform(get("/api/brands"))
                .andExpect(status().isOk());
    }
    @Test
    void testGetBrandById() throws Exception {
        Brand brand = new Brand(1L, "Renault", "French automobile manufacturer", List.of());
        when(brandService.getBrandById(1L)).thenReturn(Optional.of(brand));

        mockMvc.perform(get("/api/brands/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBrandByIdNotFound() throws Exception {
        when(brandService.getBrandById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/brands/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateBrand() throws Exception {
        Brand brand = new Brand(1L, "New Brand", "A new brand", List.of());
        when(brandService.saveBrand(any())).thenReturn(brand);

        String brandJson = "{\"name\":\"New Brand\",\"description\":\"A new brand\"}";
        mockMvc.perform(post("/api/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(brandJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateBrand() throws Exception {
        Brand brand = new Brand(1L, "Updated Brand", "Updated description", List.of());
        when(brandService.getBrandById(1L)).thenReturn(Optional.of(brand));
        when(brandService.saveBrand(any())).thenReturn(brand);

        String brandJson = "{\"name\":\"Updated Brand\",\"description\":\"Updated description\"}";
        mockMvc.perform(put("/api/brands/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(brandJson))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateBrandNotFound() throws Exception {
        when(brandService.getBrandById(999L)).thenReturn(Optional.empty());

        String brandJson = "{\"name\":\"Updated Brand\",\"description\":\"Updated description\"}";
        mockMvc.perform(put("/api/brands/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(brandJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteBrand() throws Exception {
        Brand brand = new Brand(1L, "Renault", "French automobile manufacturer", List.of());
        when(brandService.getBrandById(1L)).thenReturn(Optional.of(brand));

        mockMvc.perform(delete("/api/brands/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteBrandNotFound() throws Exception {
        when(brandService.getBrandById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/brands/999"))
                .andExpect(status().isNotFound());
    }
}
