package com.indra.dronmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indra.dronmanager.model.Dron;

@Repository
public interface DronRepository extends JpaRepository<Dron, Integer>{
    boolean existsByXAndY(int x, int y);

    Optional<Dron> findByXAndY(int x, int y);
}
