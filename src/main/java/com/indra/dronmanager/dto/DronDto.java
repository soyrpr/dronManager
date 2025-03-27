package com.indra.dronmanager.dto;

import java.util.List;

import com.indra.dronmanager.model.Ordenes;
import com.indra.dronmanager.model.Orientacion;

import lombok.Data;

@Data
public class DronDto {
    private int id;
    private String nombre;
    private String modelo;
    private int x;
    private int y;
    private Orientacion orientacion;
    private List<Ordenes> ordenes;
    private Long matrizVueloId;
}
