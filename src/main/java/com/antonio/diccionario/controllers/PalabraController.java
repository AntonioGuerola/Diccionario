package com.antonio.diccionario.controllers;

import com.antonio.diccionario.exceptions.RecordNotFoundException;
import com.antonio.diccionario.models.Definicion;
import com.antonio.diccionario.models.Palabra;
import com.antonio.diccionario.services.PalabraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/palabras")
public class PalabraController {
    @Autowired
    private PalabraService palabraService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Palabra>> findAll(){
        List<Palabra> list = palabraService.getAllPalabras();
        for (Palabra palabra : list) {
            palabra.setDefinicions(null);
        }
        return new ResponseEntity<List<Palabra>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Palabra> getPalabraById(@PathVariable Integer id) throws RecordNotFoundException {
        Palabra palabra = palabraService.getPalabraById(id);
        palabra.setDefinicions(null);
        return new ResponseEntity<Palabra>(palabra, new HttpHeaders(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Palabra> createPalabra(@RequestBody Palabra palabra) {
        Palabra createdPalabra = palabraService.createPalabra(palabra);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPalabra);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Palabra> updatePalabra(@PathVariable Integer id, @RequestBody Palabra updatedPalabra) throws RecordNotFoundException {
        Palabra palabraUpdated = palabraService.updatePalabra(id, updatedPalabra);
        return ResponseEntity.status(HttpStatus.OK).body(palabraUpdated);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public HttpStatus deletePalabrabyId(@PathVariable Integer id) throws RecordNotFoundException {
        palabraService.deletePalabra(id);
        return HttpStatus.ACCEPTED;
    }

    @CrossOrigin
    @GetMapping("/search/{termino}")
    public ResponseEntity<List<Palabra>> getPalabraByTermino(@PathVariable String termino) {
        List<Palabra> palabrasList = palabraService.getPalabrasByTermino(termino);
        return new ResponseEntity<List<Palabra>>(palabrasList, new HttpHeaders(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}/definiciones")
    public ResponseEntity<List<Definicion>> getDefinicionesByPalabraId(@PathVariable Integer id) throws RecordNotFoundException {
        List<Definicion> definiciones = palabraService.getDefinicionesByPalabraId(id);
        return new ResponseEntity<List<Definicion>>(definiciones, new HttpHeaders(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/con-definiciones")
    public ResponseEntity<Palabra> createPalabraWithDefiniciones(@RequestBody Palabra palabra) {
        Palabra createdPalabra = palabraService.createPalabraWithDefiniciones(palabra);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPalabra);
    }

    @CrossOrigin
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Palabra>> getPalabrasByCategoriaGramatical(@PathVariable String categoria) {
        List<Palabra> palabrasList = palabraService.getPalabrasByCategoriaGramatical(categoria);
        return new ResponseEntity<List<Palabra>>(palabrasList, new HttpHeaders(), HttpStatus.OK);
    }
}
