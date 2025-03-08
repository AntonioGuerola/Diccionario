package com.antonio.diccionario.controllers;

import com.antonio.diccionario.exceptions.RecordNotFoundException;
import com.antonio.diccionario.models.Definicion;
import com.antonio.diccionario.models.Palabra;
import com.antonio.diccionario.services.PalabraService;
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
@RequestMapping("/palabras")
@Tag(name = "Palabras", description = "Operaciones relacionadas con las palabras del diccionario")
public class PalabraController {
    @Autowired
    private PalabraService palabraService;

    @Operation(summary = "Obtener todas las palabras", description = "Retorna una lista de todas las palabras en el diccionario.")
    @ApiResponse(responseCode = "200", description = "Lista de palabras encontrada")
    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Palabra>> findAll() {
        List<Palabra> list = palabraService.getAllPalabras();
        for (Palabra palabra : list) {
            palabra.setDefinicions(null);
        }
        return new ResponseEntity<List<Palabra>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener una palabra por ID", description = "Retorna una palabra específica basada en su ID.")
    @ApiResponse(responseCode = "200", description = "Palabra encontrada")
    @ApiResponse(responseCode = "404", description = "Palabra no encontrada")
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Palabra> getPalabraById(
            @Parameter(description = "ID de la palabra a buscar", required = true)
            @PathVariable Integer id) throws RecordNotFoundException {
        Palabra palabra = palabraService.getPalabraById(id);
        palabra.setDefinicions(null);
        return new ResponseEntity<Palabra>(palabra, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Crear una nueva palabra", description = "Crea una nueva palabra en el diccionario.")
    @ApiResponse(responseCode = "201", description = "Palabra creada exitosamente")
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Palabra> createPalabra(@RequestBody Palabra palabra) {
        Palabra createdPalabra = palabraService.createPalabra(palabra);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPalabra);
    }

    @Operation(summary = "Actualizar una palabra existente", description = "Actualiza una palabra existente en el diccionario.")
    @ApiResponse(responseCode = "200", description = "Palabra actualizada exitosamente")
    @ApiResponse(responseCode = "404", description = "Palabra no encontrada")
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Palabra> updatePalabra(
            @Parameter(description = "ID de la palabra a actualizar", required = true)
            @PathVariable Integer id, @RequestBody Palabra updatedPalabra) throws RecordNotFoundException {
        Palabra palabraUpdated = palabraService.updatePalabra(id, updatedPalabra);
        return ResponseEntity.status(HttpStatus.OK).body(palabraUpdated);
    }

    @Operation(summary = "Eliminar una palabra", description = "Elimina una palabra del diccionario.")
    @ApiResponse(responseCode = "202", description = "Palabra eliminada exitosamente")
    @ApiResponse(responseCode = "404", description = "Palabra no encontrada")
    @CrossOrigin
    @DeleteMapping("/{id}")
    public HttpStatus deletePalabrabyId(
            @Parameter(description = "ID de la palabra a eliminar", required = true)
            @PathVariable Integer id) throws RecordNotFoundException {
        palabraService.deletePalabra(id);
        return HttpStatus.ACCEPTED;
    }

    @Operation(summary = "Buscar palabras por término", description = "Retorna una lista de palabras que coinciden con el término de búsqueda.")
    @ApiResponse(responseCode = "200", description = "Lista de palabras encontrada")
    @CrossOrigin
    @GetMapping("/search/{termino}")
    public ResponseEntity<List<Palabra>> getPalabraByTermino(
            @Parameter(description = "Término de búsqueda", required = true)
            @PathVariable String termino) {
        List<Palabra> palabrasList = palabraService.getPalabrasByTermino(termino);
        return new ResponseEntity<List<Palabra>>(palabrasList, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener definiciones de una palabra", description = "Retorna una lista de definiciones asociadas a una palabra específica.")
    @ApiResponse(responseCode = "200", description = "Lista de definiciones encontrada")
    @ApiResponse(responseCode = "404", description = "Palabra no encontrada")
    @CrossOrigin
    @GetMapping("/{id}/definiciones")
    public ResponseEntity<List<Definicion>> getDefinicionesByPalabraId(
            @Parameter(description = "ID de la palabra", required = true)
            @PathVariable Integer id) throws RecordNotFoundException {
        List<Definicion> definiciones = palabraService.getDefinicionesByPalabraId(id);
        return new ResponseEntity<List<Definicion>>(definiciones, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Crear una palabra con definiciones", description = "Crea una nueva palabra junto con sus definiciones en el diccionario.")
    @ApiResponse(responseCode = "201", description = "Palabra y definiciones creadas exitosamente")
    @CrossOrigin
    @PostMapping("/con-definiciones")
    public ResponseEntity<Palabra> createPalabraWithDefiniciones(@RequestBody Palabra palabra) {
        Palabra createdPalabra = palabraService.createPalabraWithDefiniciones(palabra);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPalabra);
    }

    @Operation(summary = "Obtener palabras por categoría gramatical", description = "Retorna una lista de palabras que pertenecen a una categoría gramatical específica.")
    @ApiResponse(responseCode = "200", description = "Lista de palabras encontrada")
    @CrossOrigin
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Palabra>> getPalabrasByCategoriaGramatical(
            @Parameter(description = "Categoría gramatical", required = true)
            @PathVariable String categoria) {
        List<Palabra> palabrasList = palabraService.getPalabrasByCategoriaGramatical(categoria);
        return new ResponseEntity<List<Palabra>>(palabrasList, new HttpHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener palabras por inicial", description = "Retorna una lista de palabras que comienzan con la letra especificada.")
    @ApiResponse(responseCode = "200", description = "Lista de palabras encontrada")
    @ApiResponse(responseCode = "404", description = "No se encontraron palabras que comiencen con la letra especificada")
    @CrossOrigin
    @GetMapping("/inicial/{letra}")
    public ResponseEntity<List<Palabra>> getPalabrasByInicial(
            @Parameter(description = "Letra inicial para buscar palabras", required = true)
            @PathVariable String letra) {
        List<Palabra> palabrasList = palabraService.getPalabrasByInicial(letra);
        return new ResponseEntity<List<Palabra>>(palabrasList, new HttpHeaders(), HttpStatus.OK);
    }
}