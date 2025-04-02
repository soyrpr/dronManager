package com.indra.dronmanager;

import com.indra.dronmanager.dto.DronDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(DronDto.class)
class DronDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testDronDtoValido() {
        DronDto dronDto = new DronDto();
        dronDto.setNombre("Dron1");
        dronDto.setModelo("Modelo1");
        dronDto.setX(5);
        dronDto.setY(10);
        dronDto.setOrientacion("N");
        dronDto.setMatrizVueloId(1L);

        Set<ConstraintViolation<DronDto>> violations = validator.validate(dronDto);
        assertTrue(violations.isEmpty(), "El DTO debe ser válido sin violaciones.");
    }

    @Test
    void testNombreNoDebeEstarVacio() {
        DronDto dronDto = new DronDto();
        dronDto.setNombre("");
        dronDto.setModelo("Modelo1");
        dronDto.setX(5);
        dronDto.setY(10);
        dronDto.setOrientacion("N");
        dronDto.setMatrizVueloId(1L);

        Set<ConstraintViolation<DronDto>> violations = validator.validate(dronDto);
        assertFalse(violations.isEmpty(), "Debe haber una violación si el nombre está vacío.");
    }

    @Test
    void testModeloNoDebeEstarVacio() {
        DronDto dronDto = new DronDto();
        dronDto.setNombre("Dron1");
        dronDto.setModelo("");
        dronDto.setX(5);
        dronDto.setY(10);
        dronDto.setOrientacion("N");
        dronDto.setMatrizVueloId(1L);

        Set<ConstraintViolation<DronDto>> violations = validator.validate(dronDto);
        assertFalse(violations.isEmpty(), "Debe haber una violación si el modelo está vacío.");
    }

    @Test
    void testCoordenadaXDebeSerPositiva() {
        DronDto dronDto = new DronDto();
        dronDto.setNombre("Dron1");
        dronDto.setModelo("Modelo1");
        dronDto.setX(-1);
        dronDto.setY(10);
        dronDto.setOrientacion("N");
        dronDto.setMatrizVueloId(1L);

        Set<ConstraintViolation<DronDto>> violations = validator.validate(dronDto);
        assertFalse(violations.isEmpty(), "Debe haber una violación si X es menor a 0.");
    }

    @Test
    void testCoordenadaYDebeSerPositiva() {
        DronDto dronDto = new DronDto();
        dronDto.setNombre("Dron1");
        dronDto.setModelo("Modelo1");
        dronDto.setX(5);
        dronDto.setY(-1);
        dronDto.setOrientacion("N");
        dronDto.setMatrizVueloId(1L);

        Set<ConstraintViolation<DronDto>> violations = validator.validate(dronDto);
        assertFalse(violations.isEmpty(), "Debe haber una violación si Y es menor a 0.");
    }

    @Test
    void testMatrizVueloIdNoDebeSerNulo() {
        DronDto dronDto = new DronDto();
        dronDto.setNombre("Dron1");
        dronDto.setModelo("Modelo1");
        dronDto.setX(5);
        dronDto.setY(10);
        dronDto.setOrientacion("N");
        dronDto.setMatrizVueloId(null);

        Set<ConstraintViolation<DronDto>> violations = validator.validate(dronDto);
        assertFalse(violations.isEmpty(), "Debe haber una violación si matrizVueloId es nulo.");
    }

    @Test
    void testSetOrientacionConValorInvalidoDebeLanzarExcepcion() {
        DronDto dronDto = new DronDto();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dronDto.setOrientacion("INVALIDO");
        });

        assertEquals("Valor de orientación inválido: INVALIDO", exception.getMessage());
    }
}
