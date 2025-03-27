package com.indra.dronmanager.service;

import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.dto.MatrizVueloDTO;


public interface MatrizVueloService {
    public MatrizVuelo guardarMatrizConDrones(MatrizVueloDTO matrizDTO);
    MatrizVuelo crearMatriz(MatrizVueloDTO matrizDTO);
    public MatrizVuelo obtenerMatrizPorId(Long id);
    public MatrizVuelo modificarMatriz(MatrizVuelo matrizVieja);
}
