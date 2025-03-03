package com.antonio.diccionario.services;

import com.antonio.diccionario.exceptions.RecordNotFoundException;
import com.antonio.diccionario.models.Palabra;
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
            throw new RecordNotFoundException("No existe Definición para el id: ", id);
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
                newPalabra.setTermino(palabra.getTermino());
                newPalabra.setCategoriaGramatical(palabra.getCategoriaGramatical());
                newPalabra.setDefinicions(palabra.getDefinicions());
                newPalabra = palabraRepository.save(newPalabra);
                return newPalabra;
            } else {
                throw new RecordNotFoundException("No existe Definición para el id: ", palabra.getId());
            }
        } else {
            throw new RecordNotFoundException("No hay id en la Definición a actualizar ", 0l);
        }
    }

    public void deletePalabra(Integer id) throws RecordNotFoundException {
        Optional<Palabra> palabraOptional = palabraRepository.findById(id);
        if (palabraOptional.isPresent()) {
            palabraRepository.delete(palabraOptional.get());
        } else {
            throw new RecordNotFoundException("No existe Definición para el id: ", id);
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
}
