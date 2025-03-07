package com.antonio.diccionario.repositories;

import com.antonio.diccionario.models.Definicion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefinicionRepository extends JpaRepository<Definicion, Integer> {
    List<Definicion> findByPalabraId(Integer palabraId);
}
