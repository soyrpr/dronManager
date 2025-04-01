package com.indra.dronmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.indra.dronmanager.dto.DronDto;
import com.indra.dronmanager.model.Dron;
import com.indra.dronmanager.model.MatrizVuelo;
import com.indra.dronmanager.model.Ordenes;
import com.indra.dronmanager.model.Orientacion;
import com.indra.dronmanager.repository.DronRepository;
import com.indra.dronmanager.repository.MatrizVueloRepository;
import com.indra.dronmanager.service.DronServiceImpl;

@ExtendWith(MockitoExtension.class) 
public class DronServiceTest {

    @Mock
    private DronRepository dronRepository;

    @Mock
    private MatrizVueloRepository matrizVueloRepository;

    @InjectMocks
    private DronServiceImpl dronService;

    private DronDto dronDto;
    private MatrizVuelo matrizVuelo;

    @BeforeEach
    void setUp(){
        dronDto = new DronDto();
        dronDto.setNombre("Dron Test");
        dronDto.setModelo("Modelo Test");
        dronDto.setX(5);
        dronDto.setY(5);
        dronDto.setOrientacion("N");

        matrizVuelo = new MatrizVuelo();
        matrizVuelo.setMaxX(10);
        matrizVuelo.setMaxY(10);
    }

    @Test
    void testCrearDron(){
        Dron dronMock = new Dron();
        dronMock.setId(1);
        dronMock.setNombre("Dron Test");
        dronMock.setX(5);
        dronMock.setY(5);
        when(dronRepository.save(any(Dron.class))).thenReturn(dronMock);

        Dron dronCreado = dronService.crearDron(dronDto, matrizVuelo);

        verify(dronRepository).save(any(Dron.class));

        assertEquals("Dron Test", dronCreado.getNombre());
        assertEquals(5, dronCreado.getX());
        assertEquals(5, dronCreado.getY());
    }

    @Test
    void testCrearDronMatrizNula(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->{ dronService.crearDron(dronDto, null);});

        assertEquals("La matriz de vuelo no puede ser nula", exception.getMessage());
    }

    @Test
    void testCrearDronMismascoordenadas(){

        when(dronRepository.existsByXAndY(5, 5)).thenReturn(true);

        DronDto dronNuevo = new DronDto();
        dronNuevo.setId(2);
        dronNuevo.setNombre("Dron Test");
        dronNuevo.setX(5);
        dronNuevo.setY(5);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dronService.crearDron(dronNuevo, matrizVuelo);  
        });
        assertEquals("Ya hay un dron en las coordenadas introducidas.", exception.getMessage());
    }

    @Test
    void testEditarDron(){
        Dron dronExistente = new Dron();
        dronExistente.setId(1);
        dronExistente.setNombre("Dron Antiguo");
        dronExistente.setX(2);
        dronExistente.setY(2);

        when(dronRepository.findById(1)).thenReturn(Optional.of(dronExistente));
        when(dronRepository.save(any(Dron.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Dron dronEditado = dronService.editarDron(1, dronDto);

        assertEquals(dronDto.getNombre(), dronEditado.getNombre());
        assertEquals(dronDto.getX(), dronEditado.getX());
        assertEquals(dronDto.getY(), dronEditado.getY());
    }

    @Test
    void testEditarDronMismasCoord(){
        when(dronRepository.existsByXAndY(5, 5)).thenReturn(true);

        Dron dronNuevo = new Dron();
        dronNuevo.setId(1);
        dronNuevo.setNombre("Dron Antiguo");
        dronNuevo.setX(1);
        dronNuevo.setY(3);

        when(dronRepository.findById(1)).thenReturn(Optional.of(dronNuevo));

        DronDto dronDto = new DronDto();
        dronDto.setNombre("Dron editado");
        dronDto.setX(5);
        dronDto.setY(5);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dronService.editarDron(1, dronDto);  
        });
        assertEquals("Ya hay un dron en las coordenadas introducidas.", exception.getMessage());
    }

    @Test
    void testEliminarDron(){
        Dron dron = new Dron();
        dron.setId(1);

        when(dronRepository.findById(1)).thenReturn(Optional.of(dron));

        dronService.eliminar(1);

        verify(dronRepository).delete(dron);
    }

    @Test
    void testObtenerDron(){
        Dron dron = new Dron();
        dron.setId(1);
        dron.setX(5);
        dron.setY(5);

        when(dronRepository.findByXAndY(5, 5)).thenReturn(Optional.of(dron));

        Dron dronObtenido = dronService.obtenerDron(5, 5);

        assertEquals(5, dronObtenido.getX());
        assertEquals(5, dronObtenido.getY());

        verify(dronRepository).findByXAndY(5, 5);
    }

    @Test
    void testMoverDron(){
        Dron dron = new Dron();
        dron.setX(5);
        dron.setY(5);
        dron.setOrientacion(Orientacion.valueOf("N"));
        dron.setMatrizVuelo(matrizVuelo);

        when(dronRepository.findById(1)).thenReturn(Optional.of(dron));
        when(dronRepository.save(any(Dron.class))).thenAnswer(invocation ->invocation.getArgument(0));

        List<Ordenes> ordenes = List.of(Ordenes.MOVE_FORWARD, Ordenes.MOVE_FORWARD);
        Dron dronMovido = dronService.moverDron(1, ordenes);

        assertEquals(7, dronMovido.getY());  

        verify(dronRepository, times(2)).save(any(Dron.class));
    }

    @Test
    void testMoverGrupoDrones(){
        Dron dronA = new Dron();
        dronA.setId(5);
        dronA.setNombre("Dron A");
        dronA.setModelo("modelo alphs");
        dronA.setX(2);
        dronA.setY(7);
        dronA.setOrientacion(Orientacion.valueOf("N"));
        dronA.setMatrizVuelo(matrizVuelo);
    
        Dron dronB = new Dron();
        dronB.setId(6);
        dronB.setNombre("Dron B");
        dronB.setModelo("modelo Beta");
        dronB.setX(6);
        dronB.setY(3);
        dronB.setOrientacion(Orientacion.valueOf("O"));
        dronB.setMatrizVuelo(matrizVuelo);
    
        List<Integer> dronsIds = List.of(5, 6);
        List<Ordenes> ordenes = List.of(Ordenes.MOVE_FORWARD, Ordenes.MOVE_FORWARD);
    
        when(dronRepository.findAllById(dronsIds)).thenReturn(List.of(dronA, dronB));
        when(dronRepository.save(any(Dron.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
        List<Dron> dronesMovidos = dronService.moverDronesGrupales(dronsIds, ordenes);
    
        assertEquals(5, dronesMovidos.get(0).getId());
        assertEquals(6, dronesMovidos.get(1).getId());
    
        verify(dronRepository, atLeast(2)).save(any(Dron.class));
    }
    
    @Test
    void testListarTodosLosDrones(){

        Dron dron1 = new Dron();
        dron1.setNombre("Dron 1");
        dron1.setModelo("Modelo A");

        
        Dron dron2 = new Dron();
        dron2.setNombre("Dron 2");
        dron2.setModelo("Modelo H");

        List<Dron> dronesLista = List.of(dron1, dron2);

        when(dronRepository.findAll()).thenReturn(dronesLista);
        
        List<Dron> dronesObtenidos = dronService.obtenerTodosLosDrones();

        assertEquals(2, dronesObtenidos.size());
    }

    @Test
    void testLimitesN(){
        Dron dron1 = new Dron();
        dron1.setX(5);
        dron1.setY(5);
        dron1.setOrientacion(Orientacion.N);
        dron1.setOrdenes(List.of(Ordenes.MOVE_FORWARD));

        MatrizVuelo matrizVuelo = new MatrizVuelo(null,5,5, new ArrayList<>());
        dron1.setMatrizVuelo(matrizVuelo);

        RuntimeException exception = assertThrows(RuntimeException.class, ()->{ dronService.ejecutarOrdenes(dron1);;});

        assertEquals("El dron saldria fuera de los limites del espacio asignado.", exception.getMessage());

    }

    @Test
    void testLimitesS(){
        Dron dron1 = new Dron();
        dron1.setX(5);
        dron1.setY(0);
        dron1.setOrientacion(Orientacion.S);
        dron1.setOrdenes(List.of(Ordenes.MOVE_FORWARD));

        MatrizVuelo matrizVuelo = new MatrizVuelo(null,5,5, new ArrayList<>());
        dron1.setMatrizVuelo(matrizVuelo);

        RuntimeException exception = assertThrows(RuntimeException.class, ()->{ dronService.ejecutarOrdenes(dron1);;});

        assertEquals("El dron saldria fuera de los limites del espacio asignado.", exception.getMessage());
    }

    @Test
    void testLimitesE(){
        Dron dron1 = new Dron();
        dron1.setX(5);
        dron1.setY(5);
        dron1.setOrientacion(Orientacion.E);
        dron1.setOrdenes(List.of(Ordenes.MOVE_FORWARD));

        MatrizVuelo matrizVuelo = new MatrizVuelo(null,5,5, new ArrayList<>());
        dron1.setMatrizVuelo(matrizVuelo);

        RuntimeException exception = assertThrows(RuntimeException.class, ()->{ dronService.ejecutarOrdenes(dron1);;});

        assertEquals("El dron saldria fuera de los limites del espacio asignado.", exception.getMessage());
    }

    @Test
    void testLimitesO(){
        Dron dron1 = new Dron();
        dron1.setX(0);
        dron1.setY(5);
        dron1.setOrientacion(Orientacion.O);
        dron1.setOrdenes(List.of(Ordenes.MOVE_FORWARD));

        MatrizVuelo matrizVuelo = new MatrizVuelo(null,5,5, new ArrayList<>());
        dron1.setMatrizVuelo(matrizVuelo);

        RuntimeException exception = assertThrows(RuntimeException.class, ()->{ dronService.ejecutarOrdenes(dron1);;});

        assertEquals("El dron saldria fuera de los limites del espacio asignado.", exception.getMessage());
    }

    @Test
    void testGirarIzq(){
        Dron dronN = new Dron();
        dronN.setX(5);
        dronN.setY(5);
        dronN.setOrientacion(Orientacion.N);
        List<Ordenes> ordenesN = List.of(Ordenes.TURN_LEFT);
        dronN.setOrdenes(ordenesN);

        dronService.ejecutarOrdenes(dronN);

        assertEquals(Orientacion.O, dronN.getOrientacion());

        Dron dronS = new Dron();
        dronS.setX(5);
        dronS.setY(5);
        dronS.setOrientacion(Orientacion.S);
        List<Ordenes> ordenesS = List.of(Ordenes.TURN_LEFT);
        dronS.setOrdenes(ordenesS);

        dronService.ejecutarOrdenes(dronS);

        assertEquals(Orientacion.E, dronS.getOrientacion());

        Dron dronE = new Dron();
        dronE.setX(5);
        dronE.setY(5);
        dronE.setOrientacion(Orientacion.E);
        List<Ordenes> ordenesE = List.of(Ordenes.TURN_LEFT);
        dronE.setOrdenes(ordenesE);

        dronService.ejecutarOrdenes(dronE);

        assertEquals(Orientacion.N, dronE.getOrientacion());

        Dron dronO = new Dron();
        dronO.setX(5);
        dronO.setY(5);
        dronO.setOrientacion(Orientacion.O);
        List<Ordenes> ordenesO = List.of(Ordenes.TURN_LEFT);
        dronO.setOrdenes(ordenesO);

        dronService.ejecutarOrdenes(dronO);

        assertEquals(Orientacion.S, dronO.getOrientacion());
    }

    @Test
    void testGirarDER(){
        Dron dronN = new Dron();
        dronN.setX(5);
        dronN.setY(5);
        dronN.setOrientacion(Orientacion.N);
        List<Ordenes> ordenesN = List.of(Ordenes.TURN_RIGHT);
        dronN.setOrdenes(ordenesN);

        dronService.ejecutarOrdenes(dronN);

        assertEquals(Orientacion.E, dronN.getOrientacion());

        Dron dronS = new Dron();
        dronS.setX(5);
        dronS.setY(5);
        dronS.setOrientacion(Orientacion.S);
        List<Ordenes> ordenesS = List.of(Ordenes.TURN_RIGHT);
        dronS.setOrdenes(ordenesS);

        dronService.ejecutarOrdenes(dronS);

        assertEquals(Orientacion.O, dronS.getOrientacion());

        Dron dronE = new Dron();
        dronE.setX(5);
        dronE.setY(5);
        dronE.setOrientacion(Orientacion.E);
        List<Ordenes> ordenesE = List.of(Ordenes.TURN_RIGHT);
        dronE.setOrdenes(ordenesE);

        dronService.ejecutarOrdenes(dronE);

        assertEquals(Orientacion.S, dronE.getOrientacion());

        Dron dronO = new Dron();
        dronO.setX(5);
        dronO.setY(5);
        dronO.setOrientacion(Orientacion.O);
        List<Ordenes> ordenesO = List.of(Ordenes.TURN_RIGHT);
        dronO.setOrdenes(ordenesO);

        dronService.ejecutarOrdenes(dronO);

        assertEquals(Orientacion.N, dronO.getOrientacion());

    }

    @Test
    void testmoverAdelante(){
        Dron dronN = new Dron();
        dronN.setX(5);
        dronN.setY(5);
        dronN.setOrientacion(Orientacion.N);
        List<Ordenes> ordenesN = List.of(Ordenes.MOVE_FORWARD);
        dronN.setOrdenes(ordenesN);
        dronN.setMatrizVuelo(matrizVuelo);

        dronService.ejecutarOrdenes(dronN);

        assertEquals(6, dronN.getY());


        Dron dronS = new Dron();
        dronS.setX(5);
        dronS.setY(5);
        dronS.setOrientacion(Orientacion.S);
        List<Ordenes> ordenesS = List.of(Ordenes.MOVE_FORWARD);
        dronS.setOrdenes(ordenesS);
        dronS.setMatrizVuelo(matrizVuelo);

        dronService.ejecutarOrdenes(dronS);

        assertEquals(4, dronS.getY());

        Dron dronE = new Dron();
        dronE.setX(5);
        dronE.setY(5);
        dronE.setOrientacion(Orientacion.E);
        List<Ordenes> ordenesE = List.of(Ordenes.MOVE_FORWARD);
        dronE.setOrdenes(ordenesE);
        dronE.setMatrizVuelo(matrizVuelo);

        dronService.ejecutarOrdenes(dronE);

        assertEquals(6, dronE.getX());

        Dron dronO = new Dron();
        dronO.setX(5);
        dronO.setY(5);
        dronO.setOrientacion(Orientacion.O);
        List<Ordenes> ordenesO = List.of(Ordenes.MOVE_FORWARD);
        dronO.setOrdenes(ordenesO);
        dronO.setMatrizVuelo(matrizVuelo);

        dronService.ejecutarOrdenes(dronO);

        assertEquals(4, dronO.getX());
    }

}
