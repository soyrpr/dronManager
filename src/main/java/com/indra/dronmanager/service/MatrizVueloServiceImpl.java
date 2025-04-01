package com.indra.dronmanager.service;

import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.dto.DronDto;
import com.indra.dronmanager.dto.MatrizVueloDTO;
import com.indra.dronmanager.model.Dron;
import com.indra.dronmanager.repository.MatrizVueloRepository;
import com.indra.dronmanager.repository.DronRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de gestión de matrices de vuelo.
 * Esta clase maneja operaciones para crear, guardar, obtener y modificar
 * matrices de vuelo.
 */
@Service
public class MatrizVueloServiceImpl implements MatrizVueloService {

    private final MatrizVueloRepository matrizVueloRepository;
    private final DronRepository dronRepository;

    /**
     * Constructor que inyecta los repositorios necesarios para la gestión de
     * matrices de vuelo y drones.
     * 
     * @param matrizVueloRepository El repositorio de matrices de vuelo.
     * @param dronRepository        El repositorio de drones.
     */
    public MatrizVueloServiceImpl(MatrizVueloRepository matrizVueloRepository, DronRepository dronRepository) {
        this.matrizVueloRepository = matrizVueloRepository;
        this.dronRepository = dronRepository;
    }

    /**
     * Guarda una nueva matriz de vuelo con los drones asociados.
     * 
     * @param matrizDTO Los datos de la matriz de vuelo y los drones a guardar.
     * @return La matriz de vuelo guardada, con los drones asociados.
     */
    @Override
    @Transactional
    public MatrizVuelo guardarMatrizConDrones(MatrizVueloDTO matrizDTO) {

        MatrizVuelo matriz = new MatrizVuelo();
        matriz.setMaxX(matrizDTO.getMaxX());
        matriz.setMaxY(matrizDTO.getMaxY());

        MatrizVuelo matrizGuardada = matrizVueloRepository.save(matriz);

        for (DronDto dronDto : matrizDTO.getDrones()) {
            Dron dron = new Dron();
            dron.setNombre(dronDto.getNombre());
            dron.setModelo(dronDto.getModelo());
            dron.setX(dronDto.getX());
            dron.setY(dronDto.getY());
            dron.setOrientacion(dronDto.getOrientacion());
            dron.setOrdenes(dronDto.getOrdenes());
            dron.setMatrizVuelo(matrizGuardada);

            dronRepository.save(dron);
        }

        return matrizGuardada;
    }

    /**
     * Crea una nueva matriz de vuelo con las dimensiones especificadas.
     * 
     * @param matrizDTO Los datos de la nueva matriz de vuelo a crear.
     * @return La nueva matriz de vuelo creada.
     * @throws IllegalArgumentException Si las dimensiones de la matriz son menores
     *                                  o iguales a cero.
     */
    @Override
    public MatrizVuelo crearMatriz(MatrizVueloDTO matrizDTO) {
        if (matrizDTO.getMaxX() <= 0 || matrizDTO.getMaxY() <= 0) {
            throw new IllegalArgumentException("Las dimensiones de la matriz deben ser mayores a cero.");
        }
        MatrizVuelo matriz = new MatrizVuelo();
        matriz.setMaxX(matrizDTO.getMaxX());
        matriz.setMaxY(matrizDTO.getMaxY());
        return matrizVueloRepository.save(matriz);
    }

    /**
     * Obtiene una matriz de vuelo por su ID.
     * 
     * @param id El ID de la matriz de vuelo a obtener.
     * @return La matriz de vuelo correspondiente al ID proporcionado.
     * @throws IllegalArgumentException Si no se encuentra la matriz con el ID
     *                                  proporcionado.
     */
    @Override
    public MatrizVuelo obtenerMatrizPorId(Long id) {
        return matrizVueloRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id no encontrado."));
    }

    /**
     * Modifica una matriz de vuelo existente.
     * 
     * @param matriz La matriz de vuelo que se va a modificar.
     * @return La matriz de vuelo modificada.
     */
    @Override
    public MatrizVuelo modificarMatriz(MatrizVuelo matriz) {
        return matrizVueloRepository.save(matriz);
    }
}
