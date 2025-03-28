package com.indra.dronmanager.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.indra.dronmanager.dto.DronDto;
import com.indra.dronmanager.model.Dron;
import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.model.Ordenes;
import com.indra.dronmanager.repository.MatrizVueloRepository;
import com.indra.dronmanager.service.DronService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/dron")
@RequiredArgsConstructor
public class DronController {
    private final DronService dronService;
    private final MatrizVueloRepository matrizVueloRepository;

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Dron crearDron(@RequestBody DronDto dronDto, @RequestParam Long matrizVueloId) {
        MatrizVuelo matrizVuelo = matrizVueloRepository.findById(matrizVueloId)
                .orElseThrow(() -> new RuntimeException("Matriz de vuelo no encontrada con ID: " + matrizVueloId));
        
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

    @GetMapping("/buscar/{x}/{y}")
    public ResponseEntity<Dron> obtenerDron(@PathVariable int x, @PathVariable int y) {
        Dron dron = dronService.obtenerDron(x, y);
        return ResponseEntity.ok(dron);
    }
    
    @PutMapping("/ordenes/{dronId}")
    public ResponseEntity<Dron> moverDron(@PathVariable int dronId, @RequestBody List<Ordenes> ordenes) {
        Dron dron = dronService.moverDron(dronId, ordenes);
        return ResponseEntity.ok(dron);
    }    

    @PutMapping("/ordenesGrupales")
    public ResponseEntity<List<Dron>> moverDrones(@RequestBody Map<String, Object> request) {
        List<Integer> dronIds = (List<Integer>) request.get("dronIds");
        List <String> ordenesStrings = (List<String>) request.get("ordenes");

        List<Ordenes> ordenes = ordenesStrings.stream().map(Ordenes::valueOf).collect(Collectors.toList());

        List <Dron> drones = dronService.moverDronesGrupales(dronIds, ordenes);
        return ResponseEntity.ok(drones);
    }    
}