package com.antonio.diccionario.repositories;

import com.antonio.diccionario.models.Definicion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DefinicionRepository extends JpaRepository<Definicion, Integer> {
}
