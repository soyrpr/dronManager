package com.indra.dronmanager.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Dron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String modelo;
    private int x;
    private int y;

    @Enumerated(EnumType.STRING)
    private Orientacion orientacion;

    @ElementCollection
    @CollectionTable(name = "dron_ordenes", joinColumns = @JoinColumn(name = "dron_id"))
    @Enumerated(EnumType.STRING)
    private List<Ordenes> ordenes;

    @ManyToOne
    @JoinColumn(name = "matriz_vuelo_id", nullable = false)
    @JsonIgnore
    private MatrizVuelo matrizVuelo;

}
