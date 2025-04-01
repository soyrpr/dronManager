package com.indra.dronmanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indra.dronmanager.controller.MatrizVueloController;
import com.indra.dronmanager.dto.MatrizVueloDTO;
import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.service.MatrizVueloService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatrizVueloController.class)
class MatrizVueloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatrizVueloService matrizService;

    @Autowired
    private ObjectMapper objectMapper;

    private MatrizVueloDTO matrizVueloDTO;
    private MatrizVuelo matrizVuelo;

    @BeforeEach
    void setUp() {
        matrizVueloDTO = new MatrizVueloDTO();
        matrizVueloDTO.setMaxX(10);
        matrizVueloDTO.setMaxY(10);

        matrizVuelo = new MatrizVuelo();
        matrizVuelo.setId(1L);
        matrizVuelo.setMaxX(10);
        matrizVuelo.setMaxY(10);
    }

    @Test
    void testCrearMatriz() throws Exception {
        when(matrizService.guardarMatrizConDrones(any(MatrizVueloDTO.class))).thenReturn(matrizVuelo);

        mockMvc.perform(post("/api/matriz/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(matrizVueloDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.maxX").value(10))
                .andExpect(jsonPath("$.maxY").value(10));

        verify(matrizService, times(1)).guardarMatrizConDrones(any(MatrizVueloDTO.class));
    }

    @Test
    void testModificarMatriz() throws Exception {
        Long id = 1L;
        MatrizVueloDTO updateDTO = new MatrizVueloDTO();
        updateDTO.setMaxX(15);
        updateDTO.setMaxY(15);

        MatrizVuelo updatedMatriz = new MatrizVuelo();
        updatedMatriz.setId(id);
        updatedMatriz.setMaxX(15);
        updatedMatriz.setMaxY(15);

        // Simular el comportamiento del servicio
        when(matrizService.obtenerMatrizPorId(id)).thenReturn(matrizVuelo);
        when(matrizService.modificarMatriz(any(MatrizVuelo.class))).thenReturn(updatedMatriz);

        mockMvc.perform(put("/api/matriz/modificar/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.maxX").value(15))
                .andExpect(jsonPath("$.maxY").value(15));

        verify(matrizService, times(1)).obtenerMatrizPorId(id);
        verify(matrizService, times(1)).modificarMatriz(any(MatrizVuelo.class));
    }

    @Test
    void testModificarMatrizNotFound() throws Exception {
        Long id = 999L; // Un id que no existe

        MatrizVueloDTO updateDTO = new MatrizVueloDTO();
        updateDTO.setMaxX(15);
        updateDTO.setMaxY(15);

        // Simular que la matriz no existe
        when(matrizService.obtenerMatrizPorId(id)).thenReturn(null);

        mockMvc.perform(put("/api/matriz/modificar/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());

        verify(matrizService, times(1)).obtenerMatrizPorId(id);
        verify(matrizService, never()).modificarMatriz(any(MatrizVuelo.class));
    }
}
