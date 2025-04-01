package com.indra.dronmanager.service;

import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.dto.MatrizVueloDTO;

/**
 * Interfaz que define los métodos para gestionar las matrices de vuelo.
 * Contiene operaciones para guardar, crear, obtener y modificar matrices de
 * vuelo.
 */
public interface MatrizVueloService {

    /**
     * Guarda una nueva matriz de vuelo con drones incluidos.
     * 
     * @param matrizDTO Los datos de la matriz de vuelo a guardar.
     * @return La matriz de vuelo guardada.
     */
    public MatrizVuelo guardarMatrizConDrones(MatrizVueloDTO matrizDTO);

    /**
     * Crea una nueva matriz de vuelo a partir de los datos proporcionados.
     * 
     * @param matrizDTO Los datos de la matriz de vuelo a crear.
     * @return La nueva matriz de vuelo creada.
     */
    MatrizVuelo crearMatriz(MatrizVueloDTO matrizDTO);

    /**
     * Obtiene una matriz de vuelo por su ID.
     * 
     * @param id El ID de la matriz de vuelo a obtener.
     * @return La matriz de vuelo correspondiente al ID proporcionado.
     * @throws MatrizVueloNoEncontradoException Si no se encuentra la matriz de
     *                                          vuelo con el ID especificado.
     */
    public MatrizVuelo obtenerMatrizPorId(Long id);

    /**
     * Modifica una matriz de vuelo existente.
     * 
     * @param matrizVieja La matriz de vuelo a modificar.
     * @return La matriz de vuelo modificada.
     * @throws MatrizVueloNoEncontradoException Si no se encuentra la matriz de
     *                                          vuelo a modificar.
     */
    public MatrizVuelo modificarMatriz(MatrizVuelo matrizVieja);
}
