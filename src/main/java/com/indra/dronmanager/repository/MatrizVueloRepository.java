package com.indra.dronmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indra.dronmanager.model.MatrizVuelo;

/**
 * Repositorio para la entidad {@link MatrizVuelo}.
 * Proporciona métodos de acceso a los datos de la matriz de vuelo en la base de
 * datos.
 * Extiende {@link JpaRepository} para permitir operaciones CRUD sobre la
 * entidad {@link MatrizVuelo}.
 */
public interface MatrizVueloRepository extends JpaRepository<MatrizVuelo, Long> {
}
