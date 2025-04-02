package com.indra.dronmanager;

import com.indra.dronmanager.controller.DronController;
import com.indra.dronmanager.dto.DronDto;
import com.indra.dronmanager.model.Dron;
import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.model.Orientacion;
import com.indra.dronmanager.service.DronService;
import com.indra.dronmanager.repository.MatrizVueloRepository;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

@WebMvcTest(DronController.class)
class DronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DronService dronService;

    @MockitoBean
    private MatrizVueloRepository matrizVueloRepository;

    @InjectMocks
    private DronController dronController;

    private Dron dron;
    private DronDto dronDto;
    private MatrizVuelo matrizVuelo;

    @BeforeEach
    void setUp() {
        dronDto = new DronDto();
        dronDto.setNombre("Dron1");
        dronDto.setModelo("Modelo1");
        dronDto.setX(1);
        dronDto.setY(2);
        dronDto.setOrientacion("N");

        matrizVuelo = new MatrizVuelo();
        matrizVuelo.setId(1L);
    }

    @Test
    void testCrearDron() throws Exception {
        when(matrizVueloRepository.findById(1L)).thenReturn(java.util.Optional.of(matrizVuelo));

        Dron createdDron = new Dron();
        createdDron.setId(1L);
        createdDron.setNombre("Dron1");
        createdDron.setModelo("Modelo1");
        createdDron.setX(1);
        createdDron.setY(2);
        createdDron.setOrientacion(Orientacion.N);

        when(dronService.crearDron(Mockito.any(DronDto.class), Mockito.any(MatrizVuelo.class))).thenReturn(createdDron);

        mockMvc.perform(post("/api/dron/crear")
                .param("matrizVueloId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Dron1\",\"modelo\":\"Modelo1\",\"x\":1,\"y\":2,\"orientacion\":\"N\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Dron1"))
                .andExpect(jsonPath("$.modelo").value("Modelo1"))
                .andExpect(jsonPath("$.x").value(1))
                .andExpect(jsonPath("$.y").value(2))
                .andExpect(jsonPath("$.orientacion").value("N"));
    }

    @Test
    void testEditarDron() throws Exception {
        dron = new Dron();
        dron.setId(1L);
        dron.setNombre("Dron1");
        dron.setModelo("Modelo1");
        dron.setX(1);
        dron.setY(2);
        dron.setOrientacion(Orientacion.N);

        when(dronService.editarDron(Mockito.anyInt(), Mockito.any(DronDto.class))).thenReturn(dron);

        mockMvc.perform(put("/api/dron/editar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Dron1\",\"modelo\":\"Modelo1\",\"x\":1,\"y\":2,\"orientacion\":\"N\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Dron1"))
                .andExpect(jsonPath("$.modelo").value("Modelo1"))
                .andExpect(jsonPath("$.x").value(1))
                .andExpect(jsonPath("$.y").value(2))
                .andExpect(jsonPath("$.orientacion").value("N"));
    }

    @Test
    void testObtenerTodosLosDrones() throws Exception {
        Dron dron1 = new Dron();
        dron1.setId(1L);
        dron1.setNombre("Dron1");
        dron1.setModelo("Modelo1");
        dron1.setX(1);
        dron1.setY(2);
        dron1.setOrientacion(Orientacion.N);

        Dron dron2 = new Dron();
        dron2.setId(2L);
        dron2.setNombre("Dron2");
        dron2.setModelo("Modelo2");
        dron2.setX(3);
        dron2.setY(6);
        dron2.setOrientacion(Orientacion.S);

        when(dronService.obtenerTodosLosDrones()).thenReturn(java.util.List.of(dron1, dron2));

        mockMvc.perform(get("/api/dron/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Dron1"))
                .andExpect(jsonPath("$[0].modelo").value("Modelo1"))
                .andExpect(jsonPath("$[0].x").value(1))
                .andExpect(jsonPath("$[0].y").value(2))
                .andExpect(jsonPath("$[0].orientacion").value("N"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Dron2"))
                .andExpect(jsonPath("$[1].modelo").value("Modelo2"))
                .andExpect(jsonPath("$[1].x").value(3))
                .andExpect(jsonPath("$[1].y").value(6))
                .andExpect(jsonPath("$[1].orientacion").value("S"));
    }

    @Test
    void testObtenerDron() throws Exception {
        Dron dron1 = new Dron();
        dron1.setId(1L);
        dron1.setNombre("Dron1");
        dron1.setModelo("Modelo1");
        dron1.setX(1);
        dron1.setY(2);
        dron1.setOrientacion(Orientacion.N);

        when(dronService.obtenerDron(1, 2)).thenReturn(dron1);

        mockMvc.perform(get("/api/dron/buscar/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Dron1"))
                .andExpect(jsonPath("$.modelo").value("Modelo1"))
                .andExpect(jsonPath("$.x").value(1))
                .andExpect(jsonPath("$.y").value(2))
                .andExpect(jsonPath("$.orientacion").value("N"));
    }

    @Test
    void testMoverDron() throws Exception {
        Dron dron1 = new Dron();
        dron1.setId(1L);
        dron1.setNombre("Dron1");
        dron1.setModelo("Modelo1");
        dron1.setX(1);
        dron1.setY(2);
        dron1.setOrientacion(Orientacion.N);

        when(dronService.moverDron(Mockito.anyInt(), Mockito.anyList())).thenReturn(dron1);

        mockMvc.perform(put("/api/dron/ordenes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[\"MOVE_FORWARD\", \"TURN_LEFT\"]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Dron1"))
                .andExpect(jsonPath("$.modelo").value("Modelo1"))
                .andExpect(jsonPath("$.x").value(1))
                .andExpect(jsonPath("$.y").value(2))
                .andExpect(jsonPath("$.orientacion").value("N"));
    }

    @Test
    void testMoverDronesGrupales() throws Exception {
        List<Integer> dronIds = Arrays.asList(1, 2);

        Dron dron1 = new Dron();
        dron1.setId(1L);
        dron1.setNombre("Dron1");
        dron1.setModelo("Modelo1");
        dron1.setX(1);
        dron1.setY(2);
        dron1.setOrientacion(Orientacion.N);

        Dron dron2 = new Dron();
        dron2.setId(2L);
        dron2.setNombre("Dron2");
        dron2.setModelo("Modelo2");
        dron2.setX(3);
        dron2.setY(6);
        dron2.setOrientacion(Orientacion.E);

        Dron dron1Movido = new Dron();
        dron1Movido.setId(1L);
        dron1Movido.setNombre("Dron1");
        dron1Movido.setModelo("Modelo1");
        dron1Movido.setX(1);
        dron1Movido.setY(3);
        dron1Movido.setOrientacion(Orientacion.O);

        Dron dron2Movido = new Dron();
        dron2Movido.setId(2L);
        dron2Movido.setNombre("Dron2");
        dron2Movido.setModelo("Modelo2");
        dron2Movido.setX(4);
        dron2Movido.setY(6);
        dron2Movido.setOrientacion(Orientacion.N);

        when(dronService.moverDronesGrupales(Mockito.eq(dronIds), Mockito.anyList()))
                .thenReturn(Arrays.asList(dron1Movido, dron2Movido));

        String jsonRequest = "{ \"dronIds\": [1, 2], \"ordenes\": [\"MOVE_FORWARD\", \"TURN_LEFT\"] }";

        mockMvc.perform(put("/api/dron/ordenesGrupales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Dron1"))
                .andExpect(jsonPath("$[0].x").value(1))
                .andExpect(jsonPath("$[0].y").value(3))
                .andExpect(jsonPath("$[0].orientacion").value("O"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Dron2"))
                .andExpect(jsonPath("$[1].x").value(4))
                .andExpect(jsonPath("$[1].y").value(6))
                .andExpect(jsonPath("$[1].orientacion").value("N"));
    }

    @Test
    void testEliminarDron() throws Exception {
        mockMvc.perform(delete("/api/dron/eliminar/1"))
                .andExpect(status().isOk());
    }
}