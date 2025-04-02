package com.indra.dronmanager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.indra.dronmanager.controller.DronController;
import com.indra.dronmanager.repository.MatrizVueloRepository;
import com.indra.dronmanager.service.DronService;

@WebMvcTest(DronController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    MatrizVueloRepository matrizVueloRepository;

    @MockitoBean
    DronService dronService;

    @Test
    void testHandleGeneralException() throws Exception {
        mockMvc.perform(get("/api/some-endpoint-that-throws-exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(content()
                        .string("Ocurrió un error: No static resource api/some-endpoint-that-throws-exception."));
    }

    @Test
    void testHandleIllegalArgumentException() throws Exception {
        mockMvc.perform(post("/api/dron/crear")
                .param("matrizVueloId", "-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"Dron1\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Argumento no válido: Invalid argument provided"));
    }

}
