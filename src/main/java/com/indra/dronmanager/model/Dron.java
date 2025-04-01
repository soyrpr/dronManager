package com.indra.dronmanager.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa un dron en la matriz de vuelo.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "matrizVuelo")
public class Dron {

    /** Identificador único del dron. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Nombre del dron. */
    @Column(nullable = false)
    private String nombre;

    /** Modelo del dron. */
    @Column(nullable = false)
    private String modelo;

    /** Coordenada X del dron en la matriz de vuelo. */
    @Column(nullable = false)
    private int x;

    /** Coordenada Y del dron en la matriz de vuelo. */
    @Column(nullable = false)
    private int y;

    /** Orientación del dron (Norte, Sur, Este, Oeste). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Orientacion orientacion;

    /** Lista de órdenes de vuelo asignadas al dron. */
    @ElementCollection
    @CollectionTable(name = "dron_ordenes", joinColumns = @JoinColumn(name = "dron_id"))
    @Enumerated(EnumType.STRING)
    private List<Ordenes> ordenes;

    /** Matriz de vuelo a la que pertenece el dron. */
    @ManyToOne
    @JoinColumn(name = "matriz_vuelo_id", nullable = false)
    @JsonIgnore
    private MatrizVuelo matrizVuelo;
}
