package com.indra.dronmanager.service;

import java.util.List;

import com.indra.dronmanager.dto.DronDto;
import com.indra.dronmanager.model.Dron;
import com.indra.dronmanager.model.MatrizVuelo;

public interface DronService {
    Dron crearDron(DronDto dronDto, MatrizVuelo matrizVuelo);
    Dron editarDron(int dronId, DronDto dronDto);
    void eliminar(int dronId);
    void ejecutarOrdenes(int dronId);
    List<Dron> moverDronesGrupales(List<DronDto> drones);
    List<Dron> obtenerTodosLosDrones();
    Dron obtenerDron(int x, int y);
}
