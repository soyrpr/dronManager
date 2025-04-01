package com.indra.dronmanager.service;

import java.util.List;
import java.util.Map;

import com.indra.dronmanager.dto.DronDto;
import com.indra.dronmanager.model.Dron;
import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.model.Ordenes;

/**
 * Servicio encargado de manejar las operaciones relacionadas con los drones.
 * Proporciona métodos para crear, editar, eliminar drones, moverlos y procesar
 * órdenes.
 */
public interface DronService {

    /**
     * Crea un nuevo dron con la información proporcionada.
     * 
     * @param dronDto     El DTO con los datos del dron a crear.
     * @param matrizVuelo La matriz de vuelo asociada al dron.
     * @return El dron creado.
     */
    Dron crearDron(DronDto dronDto, MatrizVuelo matrizVuelo);

    /**
     * Edita un dron existente.
     * 
     * @param dronId  El ID del dron a editar.
     * @param dronDto El DTO con los datos actualizados del dron.
     * @return El dron actualizado.
     */
    Dron editarDron(int dronId, DronDto dronDto);

    /**
     * Elimina un dron por su ID.
     * 
     * @param dronId El ID del dron a eliminar.
     */
    void eliminar(int dronId);

    /**
     * Ejecuta las órdenes de un dron.
     * 
     * @param dron El dron sobre el que se ejecutarán las órdenes.
     */
    void ejecutarOrdenes(Dron dron);

    /**
     * Obtiene todos los drones registrados.
     * 
     * @return Una lista con todos los drones.
     */
    List<Dron> obtenerTodosLosDrones();

    /**
     * Obtiene un dron específico según sus coordenadas (x, y).
     * 
     * @param x La coordenada X del dron.
     * @param y La coordenada Y del dron.
     * @return El dron encontrado, o null si no se encuentra.
     */
    Dron obtenerDron(int x, int y);

    /**
     * Mueve un dron de acuerdo a una lista de órdenes proporcionadas.
     * 
     * @param dronId  El ID del dron a mover.
     * @param ordenes Las órdenes a ejecutar sobre el dron.
     * @return El dron después de moverlo.
     */
    Dron moverDron(int dronId, List<Ordenes> ordenes);

    /**
     * Mueve un grupo de drones de acuerdo a las órdenes proporcionadas.
     * 
     * @param dronIds Una lista con los IDs de los drones a mover.
     * @param ordenes Las órdenes a ejecutar sobre los drones.
     * @return Una lista con los drones después de moverlos.
     */
    List<Dron> moverDronesGrupales(List<Integer> dronIds, List<Ordenes> ordenes);

    /**
     * Procesa las órdenes grupales de drones a partir de una solicitud.
     * 
     * @param request El mapa que contiene la solicitud con los IDs de los drones y
     *                las órdenes a ejecutar.
     * @return Una lista con los drones después de procesar las órdenes.
     */
    List<Dron> procesarOrdenesGrupales(Map<String, Object> request);
}
