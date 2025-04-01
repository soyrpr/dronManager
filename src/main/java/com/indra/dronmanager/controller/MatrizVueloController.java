package com.indra.dronmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.indra.dronmanager.dto.MatrizVueloDTO;
import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.service.MatrizVueloService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador para gestionar la matriz de vuelo.
 * Permite la creación, modificación y consulta de matrices.
 */
@RestController
@RequestMapping("/api/matriz")
@RequiredArgsConstructor
public class MatrizVueloController {

    private final MatrizVueloService matrizService;

    /**
     * Crea una nueva matriz de vuelo junto con los drones asociados.
     *
     * @param matrizDTO Objeto DTO que contiene la configuración de la matriz.
     * @return La matriz de vuelo creada.
     */
    @PostMapping("/crear")
    public ResponseEntity<MatrizVuelo> crearMatriz(@RequestBody MatrizVueloDTO matrizDTO) {
        MatrizVuelo matriz = matrizService.guardarMatrizConDrones(matrizDTO);
        return ResponseEntity.ok(matriz);
    }

    /**
     * Modifica los valores de una matriz de vuelo existente.
     *
     * @param id        ID de la matriz de vuelo a modificar.
     * @param matrizDTO DTO con los nuevos valores de la matriz.
     * @return La matriz de vuelo actualizada o un error 404 si no se encuentra.
     */
    @PutMapping("/modificar/{id}")
    public ResponseEntity<MatrizVuelo> modificarMatriz(@PathVariable Long id, @RequestBody MatrizVueloDTO matrizDTO) {
        System.out.println("Datos para actualizar: maxX= " + matrizDTO.getMaxX() + ", maxY= " + matrizDTO.getMaxY());

        MatrizVuelo matrizVieja = matrizService.obtenerMatrizPorId(id);

        if (matrizVieja == null) {
            return ResponseEntity.notFound().build();
        }

        if (matrizDTO.getMaxX() != null) {
            matrizVieja.setMaxX(matrizDTO.getMaxX());
        }

        if (matrizDTO.getMaxY() != null) {
            matrizVieja.setMaxY(matrizDTO.getMaxY());
        }

        MatrizVuelo matrizNueva = matrizService.modificarMatriz(matrizVieja);
        return ResponseEntity.ok(matrizNueva);
    }
}
