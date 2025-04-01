package com.indra.dronmanager.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar una Matriz de Vuelo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatrizVueloDTO {

    /** Dimensión máxima en el eje X. Debe ser mayor o igual a 0. */
    @NotNull(message = "El valor de maxX no puede ser nulo.")
    @Min(value = 0, message = "maxX debe ser mayor o igual a 0.")
    private Integer maxX;

    /** Dimensión máxima en el eje Y. Debe ser mayor o igual a 0. */
    @NotNull(message = "El valor de maxY no puede ser nulo.")
    @Min(value = 0, message = "maxY debe ser mayor o igual a 0.")
    private Integer maxY;

    /** Lista de drones dentro de la matriz de vuelo. */
    private List<DronDto> drones;
}
