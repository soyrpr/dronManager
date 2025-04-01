package com.indra.dronmanager.model;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa una matriz de vuelo en la que operan los drones.
 */
@Entity
@Table(name = "matriz_vuelo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "drones")
public class MatrizVuelo {

    /** Identificador único de la matriz de vuelo. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Límite máximo en el eje X de la matriz de vuelo. */
    @Column(name = "max_x", nullable = false)
    private int maxX;

    /** Límite máximo en el eje Y de la matriz de vuelo. */
    @Column(name = "max_y", nullable = false)
    private int maxY;

    /** Lista de drones asignados a esta matriz de vuelo. */
    @OneToMany(mappedBy = "matrizVuelo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dron> drones;

}
