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
public class DefinicionService {
    @Autowired
    private DefinicionRepository definicionRepository;

    @Autowired
    private PalabraRepository palabraRepository;

    public List<Definicion> getAllDefiniciones() {
        List<Definicion> definicionList = definicionRepository.findAll();
        if (definicionList.size() > 0) {
            return definicionList;
        }else{
            return new ArrayList<Definicion>();
        }
    }

    public Definicion getDefiniionById(Integer id) throws RecordNotFoundException {
        Optional<Definicion> definicion = definicionRepository.findById(id);

        if(definicion.isPresent()){
            return definicion.get();
        }else{
            throw new RecordNotFoundException("No existe Definición para el id: ",id);
        }
    }

    public Definicion createDefinicion(Integer palabraId, Definicion definicion) {
        Optional<Palabra> palabraOptional = palabraRepository.findById(palabraId);
        if (palabraOptional.isPresent()) {
            definicion.setPalabra(palabraOptional.get());
            return definicionRepository.save(definicion);
        } else {
            throw new RecordNotFoundException("No existe la palabra con id: ", palabraId);
        }
    }

    public Definicion updateDefinicion(Integer id, Definicion definicion) throws RecordNotFoundException {
        if (definicion.getId()!=null){
            Optional<Definicion> definicionOptional = definicionRepository.findById(id);
            if (definicionOptional.isPresent()){
                Definicion newDefinicion = definicionOptional.get();
                newDefinicion.setDescripcion(definicion.getDescripcion());
                newDefinicion.setEjemplo(definicion.getEjemplo());
                newDefinicion.setPalabra(definicion.getPalabra());
                newDefinicion=definicionRepository.save(newDefinicion);
                return newDefinicion;
            }else{
                throw new RecordNotFoundException("No existe Definición para el id: ", definicion.getId());
            }
        }else{
            throw new RecordNotFoundException("No hay id en la Definición a actualizar ",0l);
        }
    }

    public void deleteDefinicion(Integer id) throws RecordNotFoundException {
        Optional<Definicion> definicionOptional = definicionRepository.findById(id);
        if (definicionOptional.isPresent()){
            definicionRepository.delete(definicionOptional.get());
        }else{
            throw new RecordNotFoundException("No existe Definición para el id: ",id);
        }
    }
}
