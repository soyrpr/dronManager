package com.indra.dronmanager.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matriz_vuelo")  
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatrizVuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "max_x")  
    private int maxX;

    @Column(name = "max_y")  
    private int maxY;

    @OneToMany(mappedBy = "matrizVuelo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dron> drones;

}
