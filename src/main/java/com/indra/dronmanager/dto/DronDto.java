package com.indra.dronmanager.dto;

import java.util.List;

import com.indra.dronmanager.model.Ordenes;
import com.indra.dronmanager.model.Orientacion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DronDto {

    private int id;

    @NotBlank(message = "El campo nombre no puede estar vacío.")
    private String nombre;

    @NotBlank(message = "El campo modelo no puede estar vacío.")
    private String modelo;

    @NotNull(message = "El valor no puede ser nulo.")
    @Min(value = 0, message = "El valor minimo del eje X debe ser mayor o igual a 0.")
    private int x;

    @NotNull(message = "El valor no puede ser nulo.")
    @Min(value = 0, message = "El valor minimo del eje Y debe ser mayor o igual a 0.")
    private int y;

    @NotNull
    private Orientacion orientacion;

    public void setOrientacion(String orientacion){
        this.orientacion = Orientacion.valueOf(orientacion);
    }

    private List<Ordenes> ordenes;
    
    @NotNull
    private Long matrizVueloId;

    public void setOrdenes(List<String> ordenes2) {
        this.ordenes = ordenes;
    }
}
