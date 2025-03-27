package com.indra.dronmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.indra.dronmanager.dto.DronDto;
import com.indra.dronmanager.model.Dron;
import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.repository.MatrizVueloRepository;
import com.indra.dronmanager.service.DronService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api/dron")
@RequiredArgsConstructor
public class DronController {
    private final DronService dronService;
    private final MatrizVueloRepository matrizVueloRepository;

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Dron crearDron(@RequestBody DronDto dronDto, @RequestParam Long matrizVueloId) {
        // Buscar la MatrizVuelo por ID
        MatrizVuelo matrizVuelo = matrizVueloRepository.findById(matrizVueloId)
                .orElseThrow(() -> new RuntimeException("Matriz de vuelo no encontrada con ID: " + matrizVueloId));
        
        // Llamar al servicio para crear el dron
        return dronService.crearDron(dronDto, matrizVuelo);
    }    

    @PutMapping("/editar/{dronId}")
    public Dron editarDron(@PathVariable("dronId") int dronId, @RequestBody DronDto dronDto){
        return dronService.editarDron(dronId, dronDto);
    }

    @DeleteMapping("/eliminar/{dronId}")
    public void eliminarDron(@PathVariable("dronId") int dronId){
        dronService.eliminar(dronId);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Dron>> listarDrones(){
        List<Dron> drones = dronService.obtenerTodosLosDrones();
        return ResponseEntity.ok(drones);
    }
    
}