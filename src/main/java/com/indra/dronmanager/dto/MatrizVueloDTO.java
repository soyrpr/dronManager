package com.indra.dronmanager.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatrizVueloDTO {
    private Integer maxX;
    private Integer maxY;
    private List<DronDto> drones;
}
