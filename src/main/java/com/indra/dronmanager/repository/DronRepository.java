package com.indra.dronmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indra.dronmanager.model.Dron;

/**
 * Repositorio para la entidad {@link Dron}.
 * Proporciona métodos de acceso a los datos de los drones en la base de datos.
 */
@Repository
public interface DronRepository extends JpaRepository<Dron, Integer> {

    /**
     * Verifica si existe un dron en una posición específica (coordenadas X, Y).
     * 
     * @param x la coordenada X del dron
     * @param y la coordenada Y del dron
     * @return true si existe un dron en esa posición, false en caso contrario
     */
    boolean existsByXAndY(int x, int y);

    /**
     * Busca un dron por sus coordenadas X e Y.
     * 
     * @param x la coordenada X del dron
     * @param y la coordenada Y del dron
     * @return un {@link Optional} que contiene el dron si se encuentra en esa
     *         posición, vacío si no
     */
    Optional<Dron> findByXAndY(int x, int y);
}
