package com.indra.dronmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.indra.dronmanager.dto.DronDto;
import com.indra.dronmanager.dto.MatrizVueloDTO;
import com.indra.dronmanager.model.Dron;
import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.model.Orientacion;
import com.indra.dronmanager.repository.DronRepository;
import com.indra.dronmanager.repository.MatrizVueloRepository;
import com.indra.dronmanager.service.MatrizVueloService;
import com.indra.dronmanager.service.MatrizVueloServiceImpl;

@ExtendWith(MockitoExtension.class) 
public class MatrizVueloServiceTests {

        @Mock
        private MatrizVueloRepository matrizVueloRepository;

        @Mock
        private DronRepository dronRepository;

        @InjectMocks
        private MatrizVueloServiceImpl matrizVueloService;


        private MatrizVueloDTO matrizVueloDTO;
        private DronDto dronDto;

        @BeforeEach
        void setUp(){
            dronDto = new DronDto();
            dronDto.setNombre("Dron 1");
            dronDto.setModelo("Modelo A");
            dronDto.setX(2);
            dronDto.setY(3);
            dronDto.setOrientacion("N");

            List<String> ordenes = new ArrayList<>();
            ordenes.add("MOVE_FORWARD");

            dronDto.setOrdenes(ordenes);

            matrizVueloDTO = new MatrizVueloDTO();
            matrizVueloDTO.setMaxX(5);
            matrizVueloDTO.setMaxY(5);
        }

        @Test
        void testGuardarMatriz() {
            DronDto dronDto = new DronDto();
            dronDto.setNombre("Dron A");
            dronDto.setModelo("Modelo 1");
            dronDto.setX(3);
            dronDto.setY(3);
            dronDto.setOrientacion("N");
        
            List<DronDto> drones = new ArrayList<>();
            drones.add(dronDto);  
        
            MatrizVueloDTO matrizVueloDTO = new MatrizVueloDTO();
            matrizVueloDTO.setMaxX(5);
            matrizVueloDTO.setMaxY(5);
            matrizVueloDTO.setDrones(drones);  
        
            MatrizVuelo matrizVuelo = new MatrizVuelo();
            matrizVuelo.setMaxX(matrizVueloDTO.getMaxX());
            matrizVuelo.setMaxY(matrizVueloDTO.getMaxY());
        
            when(matrizVueloRepository.save(any(MatrizVuelo.class))).thenReturn(matrizVuelo);
            when(dronRepository.save(any(Dron.class))).thenReturn(new Dron());
        
            MatrizVuelo matrizGuardada = matrizVueloService.guardarMatrizConDrones(matrizVueloDTO);
        
            assertNotNull(matrizGuardada);
            assertEquals(5, matrizGuardada.getMaxX());
            assertEquals(5, matrizGuardada.getMaxY());
        
            verify(dronRepository, times(1)).save(any(Dron.class));  
        }
        
        @Test
        void testCrearMatriz(){
            MatrizVueloDTO matrizdDto = new MatrizVueloDTO();
            matrizdDto.setMaxX(5);
            matrizdDto.setMaxY(5);

            MatrizVuelo matrizVuelo = new MatrizVuelo();
            matrizVuelo.setMaxX(5);
            matrizVuelo.setMaxY(5);

            when(matrizVueloRepository.save(any(MatrizVuelo.class))).thenReturn(matrizVuelo);

            MatrizVuelo matriz = matrizVueloService.crearMatriz(matrizdDto);

            assertNotNull(matriz);
            assertEquals(5, matriz.getMaxX());
            assertEquals(5, matriz.getMaxY());

            verify(matrizVueloRepository, times(1)).save(any(MatrizVuelo.class));
        }  

        @Test
        void testCrearMatrizInvalida(){
            MatrizVueloDTO matrizdDto = new MatrizVueloDTO();
            matrizdDto.setMaxX(-5);
            matrizdDto.setMaxY(-5);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                matrizVueloService.crearMatriz(matrizdDto);
            });

            assertEquals("Las dimensiones de la matriz deben ser mayores a cero.", exception.getMessage());
        }  

        @Test
        void obtenerMatrizPorIdErronea(){
            Long idErronea=999L;
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                matrizVueloService.obtenerMatrizPorId(idErronea);
            });            assertEquals("Id no encontrado.", exception.getMessage());
        }
}