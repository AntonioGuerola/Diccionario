package com.antonio.diccionario.controllers;

import com.antonio.diccionario.exceptions.RecordNotFoundException;
import com.antonio.diccionario.models.Definicion;
import com.antonio.diccionario.services.DefinicionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/definiciones")
@Tag(name = "Definiciones", description = "Operaciones relacionadas con las definiciones de las palabras")
public class DefinicionController {
    @Autowired
    private DefinicionService definicionService;

    @Operation(summary = "Obtener todas las definiciones", description = "Retorna una lista de todas las definiciones en el diccionario.")
    @ApiResponse(responseCode = "200", description = "Lista de definiciones encontrada")
    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Definicion>> findAll() {
        List<Definicion> list = definicionService.getAllDefiniciones();
        return new ResponseEntity<List<Definicion>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener una definición por ID", description = "Retorna una definición específica basada en su ID.")
    @ApiResponse(responseCode = "200", description = "Definición encontrada")
    @ApiResponse(responseCode = "404", description = "Definición no encontrada")
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Definicion> getDefinicionById(
            @Parameter(description = "ID de la definición a buscar", required = true)
            @PathVariable Integer id) throws RecordNotFoundException {
        Definicion definicion = definicionService.getDefiniionById(id);
        return new ResponseEntity<Definicion>(definicion, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Crear una nueva definición", description = "Crea una nueva definición para una palabra específica.")
    @ApiResponse(responseCode = "201", description = "Definición creada exitosamente")
    @CrossOrigin
    @PostMapping("/{palabraid}")
    public ResponseEntity<Definicion> createDefinicion(
            @Parameter(description = "ID de la palabra a la que pertenece la definición", required = true)
            @PathVariable Integer palabraid, @RequestBody Definicion definicion) {
        Definicion createdDefinicion = definicionService.createDefinicion(palabraid, definicion);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDefinicion);
    }

    @Operation(summary = "Actualizar una definición", description = "Actualiza una definición existente en el diccionario.")
    @ApiResponse(responseCode = "200", description = "Definición actualizada exitosamente")
    @ApiResponse(responseCode = "404", description = "Definición no encontrada")
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Definicion> updateDefinicion(
            @Parameter(description = "ID de la definición a actualizar", required = true)
            @PathVariable Integer id,
            @RequestBody Definicion definicion) throws RecordNotFoundException {
        Definicion updatedDefinicion = definicionService.updateDefinicion(id, definicion);
        return new ResponseEntity<Definicion>(updatedDefinicion, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar una definición", description = "Elimina una definición del diccionario.")
    @ApiResponse(responseCode = "202", description = "Definición eliminada exitosamente")
    @ApiResponse(responseCode = "404", description = "Definición no encontrada")
    @CrossOrigin
    @DeleteMapping("/{id}")
    public HttpStatus deleteDefinicionbyId(
            @Parameter(description = "ID de la definición a eliminar", required = true)
            @PathVariable Integer id) throws RecordNotFoundException {
        definicionService.deleteDefinicion(id);
        return HttpStatus.ACCEPTED;
    }
}