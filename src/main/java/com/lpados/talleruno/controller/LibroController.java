package com.lpados.talleruno.controller;

import com.lpados.talleruno.entity.Libro;
import com.lpados.talleruno.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*")
public class LibroController {
    
    @Autowired
    private LibroService libroService;
    
    @GetMapping
    public ResponseEntity<List<Libro>> getAllLibros() {
        List<Libro> libros = libroService.findAll();
        return ResponseEntity.ok(libros);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable Long id) {
        Optional<Libro> libro = libroService.findById(id);
        return libro.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Libro> getLibroByIsbn(@PathVariable String isbn) {
        Optional<Libro> libro = libroService.findByIsbn(isbn);
        return libro.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/disponibles")
    public ResponseEntity<List<Libro>> getLibrosDisponibles() {
        List<Libro> libros = libroService.findLibrosDisponibles();
        return ResponseEntity.ok(libros);
    }
    
    @GetMapping("/prestados")
    public ResponseEntity<List<Libro>> getLibrosPrestados() {
        List<Libro> libros = libroService.findLibrosPrestados();
        return ResponseEntity.ok(libros);
    }
    
    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<Libro>> buscarLibrosPorTitulo(@RequestParam String titulo) {
        List<Libro> libros = libroService.findByTituloContaining(titulo);
        return ResponseEntity.ok(libros);
    }
    
    @GetMapping("/buscar/autor")
    public ResponseEntity<List<Libro>> buscarLibrosPorAutor(@RequestParam String autor) {
        List<Libro> libros = libroService.findByAutorContaining(autor);
        return ResponseEntity.ok(libros);
    }
    
    @PostMapping
    public ResponseEntity<Libro> createLibro(@Valid @RequestBody Libro libro) {
        try {
            Optional<Libro> libroExistente = libroService.findByIsbn(libro.getIsbn());
            if (libroExistente.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            Libro nuevoLibro = libroService.save(libro);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoLibro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Libro> updateLibro(@PathVariable Long id, @Valid @RequestBody Libro libro) {
        try {
            Optional<Libro> libroExistente = libroService.findById(id);
            if (libroExistente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            libro.setId(id);
            Libro libroActualizado = libroService.save(libro);
            return ResponseEntity.ok(libroActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        try {
            Optional<Libro> libro = libroService.findById(id);
            if (libro.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            libroService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}