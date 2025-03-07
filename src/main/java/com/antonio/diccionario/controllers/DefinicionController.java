package com.antonio.diccionario.controllers;

import com.antonio.diccionario.exceptions.RecordNotFoundException;
import com.antonio.diccionario.models.Definicion;
import com.antonio.diccionario.models.Palabra;
import com.antonio.diccionario.services.DefinicionService;
import com.antonio.diccionario.services.PalabraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/definiciones")
public class DefinicionController {
    @Autowired
    private DefinicionService definicionService;

    @Autowired
    private PalabraService palabraService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Definicion>> findAll() {
        List<Definicion> list = definicionService.getAllDefiniciones();
        return new ResponseEntity<List<Definicion>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Definicion> getDefinicionById(@PathVariable Integer id) throws RecordNotFoundException {
        Definicion definicion = definicionService.getDefiniionById(id);
        return new ResponseEntity<Definicion>(definicion, new HttpHeaders(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/{palabraid}")
    public ResponseEntity<Definicion> createDefinicion(@PathVariable Integer palabraid, @RequestBody Definicion definicion) {
        Definicion createdDefinicion = definicionService.createDefinicion(palabraid, definicion);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDefinicion);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public HttpStatus deleteDefinicionbyId(@PathVariable Integer id) throws RecordNotFoundException {
        definicionService.deleteDefinicion(id);
        return HttpStatus.ACCEPTED;
    }
}
