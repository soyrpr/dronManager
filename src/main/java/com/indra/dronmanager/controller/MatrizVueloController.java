package com.indra.dronmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.indra.dronmanager.dto.MatrizVueloDTO;
import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.service.MatrizVueloService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/matriz")
@RequiredArgsConstructor
public class MatrizVueloController {

    private final MatrizVueloService matrizService;

    @PostMapping("/crear")
    public ResponseEntity<MatrizVuelo> crearMatriz(@RequestBody MatrizVueloDTO matrizDTO) {

        MatrizVuelo matriz = matrizService.guardarMatrizConDrones(matrizDTO);
        return ResponseEntity.ok(matriz); 
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<MatrizVuelo> modificarMatriz(@PathVariable Long id, @RequestBody MatrizVueloDTO matrizDTO){
        System.out.println("Datos para actualizar: maxX= " + matrizDTO.getMaxX() + ", maxY= " + matrizDTO.getMaxY());

        MatrizVuelo matrizVieja = matrizService.obtenerMatrizPorId(id);

        if(matrizVieja == null){
            return ResponseEntity.notFound().build();
        }

        if(matrizDTO.getMaxX() != null){
            matrizVieja.setMaxX(matrizDTO.getMaxX());
        }

        if(matrizDTO.getMaxY() != null){
            matrizVieja.setMaxY(matrizDTO.getMaxY());
        }

        MatrizVuelo matrizNueva = matrizService.modificarMatriz(matrizVieja);
        return ResponseEntity.ok(matrizNueva);
    }

}
