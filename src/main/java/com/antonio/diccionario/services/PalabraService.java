package com.antonio.diccionario.services;

import com.antonio.diccionario.exceptions.RecordNotFoundException;
import com.antonio.diccionario.models.Definicion;
import com.antonio.diccionario.models.Palabra;
import com.antonio.diccionario.repositories.DefinicionRepository;
import com.antonio.diccionario.repositories.PalabraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PalabraService {
    @Autowired
    private PalabraRepository palabraRepository;

    @Autowired
    private DefinicionRepository definicionRepository;

    public List<Palabra> getAllPalabras() {
        List<Palabra> palabraList = palabraRepository.findAll();
        if (palabraList.size() >= 0) {
            return palabraList;
        } else {
            return new ArrayList<Palabra>();
        }
    }

    public Palabra getPalabraById(Integer id) throws RecordNotFoundException {
        Optional<Palabra> palabra = palabraRepository.findById(id);

        if (palabra.isPresent()) {
            return palabra.get();
        } else {
            throw new RecordNotFoundException("No existe Palabra para el id: ", id);
        }
    }

    public Palabra createPalabra(Palabra palabra) {
        palabra = palabraRepository.save(palabra);
        return palabra;
    }

    public Palabra updatePalabra(Integer id, Palabra palabra) throws RecordNotFoundException {
        if (palabra.getId() != null) {
            Optional<Palabra> palabraOptional = palabraRepository.findById(id);
            if (palabraOptional.isPresent()) {
                Palabra newPalabra = palabraOptional.get();
                newPalabra.setDefinicions(palabra.getDefinicions());
                newPalabra.setTermino(palabra.getTermino());
                newPalabra.setCategoriaGramatical(palabra.getCategoriaGramatical());
                newPalabra = palabraRepository.save(newPalabra);
                return newPalabra;
            } else {
                throw new RecordNotFoundException("No existe Palabra para el id: ", palabra.getId());
            }
        } else {
            throw new RecordNotFoundException("No hay id en la Palabra a actualizar ", 0l);
        }
    }

    public void deletePalabra(Integer id) throws RecordNotFoundException {
        Optional<Palabra> palabraOptional = palabraRepository.findById(id);
        if (palabraOptional.isPresent()) {
            palabraRepository.delete(palabraOptional.get());
        } else {
            throw new RecordNotFoundException("No existe Palabra para el id: ", id);
        }
    }

    public List<Palabra> getPalabrasByTermino(String termino) {
        List<Palabra> palabrasList = palabraRepository.getByTermino(termino);
        if (!palabrasList.isEmpty()) {
            return palabrasList;
        } else {
            return new ArrayList<Palabra>();
        }
    }

    public List<Definicion> getDefinicionesByPalabraId(Integer palabraId) throws RecordNotFoundException {
        Optional<Palabra> palabraOptional = palabraRepository.findById(palabraId);
        if (palabraOptional.isPresent()) {
            return palabraOptional.get().getDefinicions(); // Retorna la lista de definiciones
        } else {
            throw new RecordNotFoundException("No existe la palabra con id: ", palabraId);
        }
    }

    public Palabra createPalabraWithDefiniciones(Palabra palabra) {
        palabra = palabraRepository.save(palabra);
        if (palabra.getDefinicions() != null) {
            for (Definicion definicion : palabra.getDefinicions()) {
                definicion.setPalabra(palabra); // Establece la relación
                definicionRepository.save(definicion); // Guarda la definición
            }
        }
        return palabra;
    }

    public List<Palabra> getPalabrasByCategoriaGramatical(String categoria) {
        List<Palabra> palabrasList = palabraRepository.findByCategoriaGramatical(categoria);
        if (!palabrasList.isEmpty()) {
            return palabrasList;
        } else {
            return new ArrayList<Palabra>(); // Retorna una lista vacía si no hay palabras
        }
    }
}
