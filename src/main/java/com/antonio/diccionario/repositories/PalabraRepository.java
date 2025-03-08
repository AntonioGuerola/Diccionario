package com.antonio.diccionario.repositories;

import com.antonio.diccionario.models.Palabra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PalabraRepository extends JpaRepository<Palabra, Integer> {
    @Query(
            value="SELECT * FROM palabra AS p WHERE p.termino LIKE %?1%",
            nativeQuery=true
    )

    public List<Palabra> getByTermino(String termino);

    List<Palabra> findByCategoriaGramatical(String categoria);

    List<Palabra> findByTerminoStartingWith(String letra);
}
