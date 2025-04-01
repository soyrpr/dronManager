package com.indra.dronmanager.dto;

import java.util.List;

import com.indra.dronmanager.model.Ordenes;
import com.indra.dronmanager.model.Orientacion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * DTO para representar un Dron en el sistema.
 */
@Data
public class DronDto {

    /** Identificador del dron. */
    private int id;

    /** Nombre del dron. */
    @NotBlank(message = "El campo nombre no puede estar vacío.")
    private String nombre;

    /** Modelo del dron. */
    @NotBlank(message = "El campo modelo no puede estar vacío.")
    private String modelo;

    /** Posición en el eje X (Debe ser >= 0). */
    @NotNull(message = "El valor no puede ser nulo.")
    @Min(value = 0, message = "El valor mínimo del eje X debe ser mayor o igual a 0.")
    private int x;

    /** Posición en el eje Y (Debe ser >= 0). */
    @NotNull(message = "El valor no puede ser nulo.")
    @Min(value = 0, message = "El valor mínimo del eje Y debe ser mayor o igual a 0.")
    private int y;

    /** Orientación del dron (NORTE, SUR, ESTE, OESTE). */
    @NotNull(message = "La orientación no puede ser nula.")
    private Orientacion orientacion;

    /** Lista de órdenes asignadas al dron. */
    private List<Ordenes> ordenes;

    /** Identificador de la matriz de vuelo a la que pertenece el dron. */
    @NotNull(message = "El ID de la matriz de vuelo no puede ser nulo.")
    private Long matrizVueloId;

    /**
     * Asigna la orientación del dron a partir de un String.
     * Maneja errores si el valor no es válido.
     *
     * @param orientacion String que representa la orientación.
     * @throws IllegalArgumentException si el valor no es válido.
     */
    public void setOrientacion(String orientacion) {
        try {
            this.orientacion = Orientacion.valueOf(orientacion.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valor de orientación inválido: " + orientacion);
        }
    }

    /**
     * Se deja vacío ya que las órdenes se asignan internamente.
     * Se recomienda implementar o eliminar el método si no se usa.
     *
     * @param ordenes Lista de órdenes en formato String.
     */
    public void setOrdenes(List<String> ordenes) {
    }
}
