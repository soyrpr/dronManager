package com.indra.dronmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.indra.dronmanager.dto.DronDto;
import com.indra.dronmanager.model.Dron;
import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.model.Ordenes;
import com.indra.dronmanager.model.Orientacion;
import com.indra.dronmanager.repository.DronRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DronServiceImpl implements DronService{
    private final DronRepository dronRepository;

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
    public void ejecutarOrdenes(Dron dron) {        
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

        dronRepository.save(dron);
    }

    private void moverAdelante(Dron dron) {
        int nuevoX = dron.getX();
        int nuevoY = dron.getY();

        switch (dron.getOrientacion()) {
            case N: 
                nuevoY +=1;
                break;
            case S: 
                nuevoY -=1;
                break;
            case E: 
                nuevoX +=1;
                break;
            case O: 
                nuevoX -=1;
                break;
        }

        MatrizVuelo matrizVuelo = dron.getMatrizVuelo();
        int maxX = matrizVuelo.getMaxX();
        int maxY = matrizVuelo.getMaxY();

        if(nuevoX < 0 || nuevoX > maxX || nuevoY < 0 || nuevoY > maxY){
            throw new RuntimeException("El dron saldria fuera de los limites del espacio asignado.");
        }
      
        
        if (dronRepository.existsByXAndY(nuevoX, nuevoY)){
            throw new RuntimeException("Ya hay un dron en la posicion asignada.");
        }

        dron.setX(nuevoX);
        dron.setY(nuevoY);
    }

    private void girarIzquierda(Dron dron) {
        switch (dron.getOrientacion()) {
            case N: dron.setOrientacion(Orientacion.O);
                break;
            case O: dron.setOrientacion(Orientacion.S);
                break;
            case S: dron.setOrientacion(Orientacion.E);
                break;
            case E: dron.setOrientacion(Orientacion.N);
                break;
        }
    }

    private void girarDerecha(Dron dron) {
        switch (dron.getOrientacion()) {
            case N: dron.setOrientacion(Orientacion.E);
                break;
            case E: dron.setOrientacion(Orientacion.S);
                break;
            case S: dron.setOrientacion(Orientacion.O);
                break;
            case O: dron.setOrientacion(Orientacion.N);
                break;
        }
    }


    @Override
    public List<Dron> moverDronesGrupales(List<Integer> dronIds, List<Ordenes> ordenes) {
        List<Dron> drones = dronRepository.findAllById(dronIds);
        if(drones.size() != dronIds.size()){
            throw new IllegalArgumentException("Alguno de los drones no han sido encontrados.");
        }

        for(Dron dron : drones) {
            dron.setOrdenes(ordenes);
            dronRepository.save(dron);
            ejecutarOrdenes(dron);
        }

        return drones;
    }

    @Override
    public Dron moverDron(int dronId, List<Ordenes> ordenes) {
        Dron dron = dronRepository.findById(dronId).orElseThrow(() -> new RuntimeException("Dron no encontrado según el ID indicado"));
        
        dron.setOrdenes(ordenes);
        dronRepository.save(dron);

        ejecutarOrdenes(dron);

        return dron;
    }
}
