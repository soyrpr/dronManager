package com.indra.dronmanager.service;

import java.util.List;

import com.indra.dronmanager.dto.DronDto;
import com.indra.dronmanager.model.Dron;
import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.model.Ordenes;

public interface DronService {
    Dron crearDron(DronDto dronDto, MatrizVuelo matrizVuelo);
    Dron editarDron(int dronId, DronDto dronDto);
    void eliminar(int dronId);
    void ejecutarOrdenes(Dron dron);
    List<Dron> obtenerTodosLosDrones();
    Dron obtenerDron(int x, int y);
    Dron moverDron(int dronId, List<Ordenes> ordenes);
    List<Dron> moverDronesGrupales(List<Integer> dronIds, List<Ordenes> ordenes);
}