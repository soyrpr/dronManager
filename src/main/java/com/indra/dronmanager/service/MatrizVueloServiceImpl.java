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


    @Override
    public MatrizVuelo crearMatriz(MatrizVueloDTO matrizDTO) {
        if (matrizDTO.getMaxX() <= 0 || matrizDTO.getMaxY() <= 0) {
            throw new IllegalArgumentException("Las dimensiones de la matriz deben ser mayores a cero.");
        }
        MatrizVuelo matriz = new MatrizVuelo();
        System.out.println(matrizDTO.getMaxX());
        matriz.setMaxX(matrizDTO.getMaxX());
        matriz.setMaxY(matrizDTO.getMaxY());
        return matrizVueloRepository.save(matriz);
    }

    @Override
    public MatrizVuelo obtenerMatrizPorId(Long id){
        return matrizVueloRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id no encontrado."));
    }

    @Override
    public MatrizVuelo modificarMatriz(MatrizVuelo matriz) {
        return matrizVueloRepository.save(matriz);
    }
}
