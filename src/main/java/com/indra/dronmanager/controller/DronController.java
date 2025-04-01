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

/**
 * Controlador para gestionar los drones dentro del sistema.
 * Proporciona endpoints para crear, editar, eliminar, listar y mover drones.
 */
@RestController
@RequestMapping("/api/dron")
@RequiredArgsConstructor
public class DronController {
    private final DronService dronService;
    private final MatrizVueloRepository matrizVueloRepository;

    /**
     * Crea un nuevo dron y lo asocia a una matriz de vuelo existente.
     *
     * @param dronDto       Datos del dron a crear.
     * @param matrizVueloId ID de la matriz de vuelo donde se añadirá el dron.
     * @return El dron creado.
     * @throws RuntimeException Si la matriz de vuelo no existe.
     */
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Dron crearDron(@RequestBody DronDto dronDto, @RequestParam Long matrizVueloId) {
        MatrizVuelo matrizVuelo = matrizVueloRepository.findById(matrizVueloId)
                .orElseThrow(() -> new RuntimeException("Matriz de vuelo no encontrada con ID: " + matrizVueloId));

        return dronService.crearDron(dronDto, matrizVuelo);
    }

    /**
     * Edita un dron existente.
     *
     * @param dronId  ID del dron a editar.
     * @param dronDto Datos actualizados del dron.
     * @return El dron actualizado.
     */
    @PutMapping("/editar/{dronId}")
    public Dron editarDron(@PathVariable("dronId") int dronId, @RequestBody DronDto dronDto) {
        return dronService.editarDron(dronId, dronDto);
    }

    /**
     * Elimina un dron del sistema.
     *
     * @param dronId ID del dron a eliminar.
     */
    @DeleteMapping("/eliminar/{dronId}")
    public void eliminarDron(@PathVariable("dronId") int dronId) {
        dronService.eliminar(dronId);
    }

    /**
     * Obtiene la lista de todos los drones registrados en el sistema.
     *
     * @return Lista de drones.
     */
    @GetMapping("/listar")
    public ResponseEntity<List<Dron>> listarDrones() {
        List<Dron> drones = dronService.obtenerTodosLosDrones();
        return ResponseEntity.ok(drones);
    }

    /**
     * Busca un dron en una posición específica de la matriz de vuelo.
     *
     * @param x Coordenada X del dron.
     * @param y Coordenada Y del dron.
     * @return El dron encontrado en la posición dada.
     */
    @GetMapping("/buscar/{x}/{y}")
    public ResponseEntity<Dron> obtenerDron(@PathVariable int x, @PathVariable int y) {
        Dron dron = dronService.obtenerDron(x, y);
        return ResponseEntity.ok(dron);
    }

    /**
     * Mueve un dron individualmente ejecutando una lista de órdenes.
     *
     * @param dronId  ID del dron a mover.
     * @param ordenes Lista de órdenes a ejecutar.
     * @return El dron actualizado después de ejecutar las órdenes.
     */
    @PutMapping("/ordenes/{dronId}")
    public ResponseEntity<Dron> moverDron(@PathVariable int dronId, @RequestBody List<Ordenes> ordenes) {
        Dron dron = dronService.moverDron(dronId, ordenes);
        return ResponseEntity.ok(dron);
    }

    /**
     * Mueve múltiples drones ejecutando órdenes grupales.
     *
     * @param request Un mapa que contiene:
     *                - "dronIds": Lista de IDs de los drones a mover.
     *                - "ordenes": Lista de órdenes a ejecutar en los drones.
     * @return Lista de drones actualizados después de ejecutar las órdenes.
     */
    @PutMapping("/ordenesGrupales")
    public ResponseEntity<List<Dron>> moverDrones(@RequestBody Map<String, Object> request) {
        List<Dron> drones = dronService.procesarOrdenesGrupales(request);
        return ResponseEntity.ok(drones);
    }
}
