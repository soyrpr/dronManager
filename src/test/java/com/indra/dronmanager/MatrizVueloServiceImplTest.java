package com.indra.dronmanager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.indra.dronmanager.dto.DronDto;
import com.indra.dronmanager.dto.MatrizVueloDTO;
import com.indra.dronmanager.model.Dron;
import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.repository.DronRepository;
import com.indra.dronmanager.repository.MatrizVueloRepository;
import com.indra.dronmanager.service.MatrizVueloServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MatrizVueloServiceImplTest {

    @Mock
    private MatrizVueloRepository matrizVueloRepository;

    @Mock
    private DronRepository dronRepository;

    @InjectMocks
    private MatrizVueloServiceImpl matrizVueloService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarMatrizConDrones() {

        MatrizVueloDTO matrizDTO = new MatrizVueloDTO();
        matrizDTO.setMaxX(10);
        matrizDTO.setMaxY(10);

        DronDto dronDto = new DronDto();
        dronDto.setNombre("Dron1");
        dronDto.setModelo("Modelo1");
        dronDto.setX(5);
        dronDto.setY(5);
        dronDto.setOrientacion("N");
        dronDto.setOrdenes(List.of("MOVE_FORWARD", "TURN_LEFT"));

        matrizDTO.setDrones(List.of(dronDto));

        MatrizVuelo matrizGuardada = new MatrizVuelo();
        matrizGuardada.setId(1L);
        matrizGuardada.setMaxX(10);
        matrizGuardada.setMaxY(10);

        when(matrizVueloRepository.save(any(MatrizVuelo.class))).thenReturn(matrizGuardada);
        when(dronRepository.save(any(Dron.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        MatrizVuelo resultado = matrizVueloService.guardarMatrizConDrones(matrizDTO);

        // Assert
        assertNotNull(resultado, "La matriz de vuelo no debe ser nula.");
        assertEquals(10, resultado.getMaxX());
        assertEquals(10, resultado.getMaxY());

        // Verificar que el repositorio de MatrizVuelo y Dron hayan sido llamados
        verify(matrizVueloRepository, times(1)).save(any(MatrizVuelo.class));
        verify(dronRepository, times(1)).save(any(Dron.class));
    }

    @Test
    void crearMatriz() {
        MatrizVueloDTO matrizDTO = new MatrizVueloDTO();
        matrizDTO.setMaxX(10);
        matrizDTO.setMaxY(10);

        MatrizVuelo matrizCreada = new MatrizVuelo();
        matrizCreada.setMaxX(10);
        matrizCreada.setMaxY(10);

        when(matrizVueloRepository.save(any(MatrizVuelo.class))).thenReturn(matrizCreada);

        MatrizVuelo resultado = matrizVueloService.crearMatriz(matrizDTO);

        assertNotNull(resultado, "La matriz de vuelo no debe ser nula.");
        assertEquals(10, resultado.getMaxX());
        assertEquals(10, resultado.getMaxY());

        verify(matrizVueloRepository, times(1)).save(any(MatrizVuelo.class));
    }

    @Test
    void crearMatrizConDimensionesInvalidas() {
        MatrizVueloDTO matrizDTO = new MatrizVueloDTO();
        matrizDTO.setMaxX(0);
        matrizDTO.setMaxY(0);

        assertThrows(IllegalArgumentException.class, () -> {
            matrizVueloService.crearMatriz(matrizDTO);
        });
    }

    @Test
    void obtenerMatrizPorId() {

        MatrizVuelo matrizGuardada = new MatrizVuelo();
        matrizGuardada.setId(1L);
        matrizGuardada.setMaxX(10);
        matrizGuardada.setMaxY(10);

        when(matrizVueloRepository.findById(1L)).thenReturn(java.util.Optional.of(matrizGuardada));

        MatrizVuelo resultado = matrizVueloService.obtenerMatrizPorId(1L);

        assertNotNull(resultado, "La matriz de vuelo no debe ser nula.");
        assertEquals(1L, resultado.getId());
        assertEquals(10, resultado.getMaxX());
        assertEquals(10, resultado.getMaxY());
    }

    @Test
    void obtenerMatrizPorIdNoEncontrada() {
        when(matrizVueloRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            matrizVueloService.obtenerMatrizPorId(1L);
        });
    }

    @Test
    void modificarMatriz() {
        MatrizVuelo matrizExistente = new MatrizVuelo();
        matrizExistente.setId(1L);
        matrizExistente.setMaxX(10);
        matrizExistente.setMaxY(10);

        when(matrizVueloRepository.save(any(MatrizVuelo.class))).thenReturn(matrizExistente);

        matrizExistente.setMaxX(20);
        matrizExistente.setMaxY(20);
        MatrizVuelo resultado = matrizVueloService.modificarMatriz(matrizExistente);

        assertNotNull(resultado, "La matriz de vuelo modificada no debe ser nula.");
        assertEquals(20, resultado.getMaxX());
        assertEquals(20, resultado.getMaxY());

        verify(matrizVueloRepository, times(1)).save(any(MatrizVuelo.class));
    }

}
