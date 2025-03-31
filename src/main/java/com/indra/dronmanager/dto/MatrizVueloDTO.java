package com.indra.dronmanager.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatrizVueloDTO {
    
    @NotNull(message = "El valor no puede ser nulo.")
    @Min(value = 0, message = "maxX debe ser mayor o igual que 0.")
    private Integer maxX;

    @NotNull(message = "El valor no puede ser nulo.")
    @Min(value = 0, message = "maxY debe ser mayor o igual que 0.")
    private Integer maxY;
    private List<DronDto> drones;
}
