package com.indra.dronmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.indra.dronmanager.dto.DronDto;
import com.indra.dronmanager.model.Dron;
import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.model.Ordenes;
import com.indra.dronmanager.repository.DronRepository;
import com.indra.dronmanager.repository.MatrizVueloRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DronServiceImpl implements DronService{
    private final DronRepository dronRepository;
    private final MatrizVueloRepository matrizVueloRepository;  

    @Override
    public Dron crearDron(DronDto dronDto, MatrizVuelo matrizVuelo) {
        if (matrizVuelo == null) {
            throw new IllegalArgumentException("La matriz de vuelo no puede ser nula");
        } else if (dronRepository.existsByXAndY(dronDto.getX(), dronDto.getY())) {
            throw new IllegalArgumentException("Ya hay un dron en las coordenadas introducidas.");
            
        }
        
        Dron dron = new Dron();
        dron.setNombre(dronDto.getNombre());
        dron.setModelo(dronDto.getModelo());
        dron.setX(dronDto.getX());
        dron.setY(dronDto.getY());
        dron.setOrientacion(dronDto.getOrientacion());
        dron.setOrdenes(dronDto.getOrdenes());
        dron.setMatrizVuelo(matrizVuelo); 
        
        return dronRepository.save(dron);
    }    
    
    @Override
    public Dron editarDron(int dronId, DronDto dronDto) {
        Dron dron = dronRepository.findById(dronId).orElseThrow(() -> new RuntimeException("Dron no encontrado."));
        
        if (dronRepository.existsByXAndY(dronDto.getX(), dronDto.getY())) {
            throw new IllegalArgumentException("Ya hay un dron en las coordenadas introducidas.");
            
        }

        dron.setNombre(dronDto.getNombre());
        dron.setModelo(dronDto.getModelo());
        dron.setX(dronDto.getX());
        dron.setY(dronDto.getY());
        dron.setOrientacion(dronDto.getOrientacion());
        dron.setOrdenes(dronDto.getOrdenes());

        return dronRepository.save(dron);
    }

    @Override
    public void eliminar(int dronId) {
        Dron dron = dronRepository.findById(dronId).orElseThrow(()-> new RuntimeException("Dron no encontrado."));

        dronRepository.delete(dron);
    }

    @Override
    public List<Dron> obtenerTodosLosDrones(){
        return dronRepository.findAll();
    }

    @Override
    public Dron obtenerDron(int x, int y){
        return dronRepository.findByXAndY(x, y).orElseThrow(() -> new RuntimeException("No se encontró el dron en esas coordenadas."));
    }


    @Override
    public void ejecutarOrdenes(int dronId) {
        Dron dron = dronRepository.findById(dronId)
            .orElseThrow(() -> new RuntimeException("Dron no encontrado con ID: " + dronId));
        
        for (Ordenes orden : dron.getOrdenes()) {
            switch (orden) {
                case MOVE_FORWARD:
                    moverAdelante(dron);
                    break;
                case TURN_LEFT:
                    girarIzquierda(dron);
                    break;
                case TURN_RIGHT:
                    girarDerecha(dron);
                    break;
            }
        }
    }

    private void moverAdelante(Dron dron) {
        // Implementación del movimiento
    }

    private void girarIzquierda(Dron dron) {
        // Implementación del giro
    }

    private void girarDerecha(Dron dron) {
        // Implementación del giro
    }


    @Override
    public List<Dron> moverDronesGrupales(List<DronDto> drones) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moverDronesGrupales'");
    }

}
