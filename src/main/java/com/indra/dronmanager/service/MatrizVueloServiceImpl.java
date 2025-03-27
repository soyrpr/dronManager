package com.indra.dronmanager.service;

import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.dto.DronDto;
import com.indra.dronmanager.dto.MatrizVueloDTO;
import com.indra.dronmanager.model.Dron;
import com.indra.dronmanager.repository.MatrizVueloRepository;
import com.indra.dronmanager.repository.DronRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatrizVueloServiceImpl implements MatrizVueloService {
    
    private final MatrizVueloRepository matrizVueloRepository;
    private final DronRepository dronRepository;

    public MatrizVueloServiceImpl(MatrizVueloRepository matrizVueloRepository, DronRepository dronRepository) {
        this.matrizVueloRepository = matrizVueloRepository;
        this.dronRepository = dronRepository;
    }

    @Override
    @Transactional
    public MatrizVuelo guardarMatrizConDrones(MatrizVueloDTO matrizDTO) {
        // Crear la matriz
        MatrizVuelo matriz = new MatrizVuelo();
        matriz.setMaxX(matrizDTO.getMaxX());
        matriz.setMaxY(matrizDTO.getMaxY());
        
        // Guardar la matriz en la base de datos
        MatrizVuelo matrizGuardada = matrizVueloRepository.save(matriz);

        // Crear los drones y asociarlos con la matriz
        for (DronDto dronDto : matrizDTO.getDrones()) {
            Dron dron = new Dron();
            dron.setNombre(dronDto.getNombre());
            dron.setModelo(dronDto.getModelo());
            dron.setX(dronDto.getX());
            dron.setY(dronDto.getY());
            dron.setOrientacion(dronDto.getOrientacion());
            dron.setOrdenes(dronDto.getOrdenes());
            dron.setMatrizVuelo(matrizGuardada); // Asociar el dron con la matriz

            // Guardar el dron en la base de datos
            dronRepository.save(dron);
        }

        return matrizGuardada;
    }


    @Override
    public MatrizVuelo crearMatriz(MatrizVueloDTO matrizDTO) {
        MatrizVuelo matriz = new MatrizVuelo();
        System.out.println(matrizDTO.getMaxX());
        matriz.setMaxX(matrizDTO.getMaxX());
        matriz.setMaxY(matrizDTO.getMaxY());
        return matrizVueloRepository.save(matriz);
    }

    @Override
    public MatrizVuelo obtenerMatrizPorId(Long id){
        return matrizVueloRepository.findById(id).orElse(null);
    }

    @Override
    public MatrizVuelo modificarMatriz(MatrizVuelo matriz) {
        return matrizVueloRepository.save(matriz);
    }
}
