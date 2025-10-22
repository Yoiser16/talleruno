package com.lpados.talleruno.controller;

import com.lpados.talleruno.entity.Prestamo;
import com.lpados.talleruno.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {
    
    @Autowired
    private PrestamoService prestamoService;
    
    @GetMapping
    public ResponseEntity<List<Prestamo>> getAllPrestamos() {
        List<Prestamo> prestamos = prestamoService.findAllWithDetails();
        return ResponseEntity.ok(prestamos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getPrestamoById(@PathVariable Long id) {
        Optional<Prestamo> prestamo = prestamoService.findById(id);
        return prestamo.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Prestamo>> getPrestamosByUsuario(@PathVariable Long usuarioId) {
        List<Prestamo> prestamos = prestamoService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(prestamos);
    }
    
    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<Prestamo>> getPrestamosByLibro(@PathVariable Long libroId) {
        List<Prestamo> prestamos = prestamoService.findByLibroId(libroId);
        return ResponseEntity.ok(prestamos);
    }
    
    @GetMapping("/libro/{libroId}/disponible")
    public ResponseEntity<Boolean> isLibroDisponible(@PathVariable Long libroId) {
        boolean disponible = prestamoService.isLibroDisponible(libroId);
        return ResponseEntity.ok(disponible);
    }
    
    @GetMapping("/vencidos")
    public ResponseEntity<List<Prestamo>> getPrestamosVencidos() {
        List<Prestamo> prestamos = prestamoService.findPrestamosVencidos();
        return ResponseEntity.ok(prestamos);
    }
    
    @GetMapping("/por-fecha-devolucion")
    public ResponseEntity<List<Prestamo>> getPrestamosByFechaDevolucion(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<Prestamo> prestamos = prestamoService.findPrestamosByFechaDevolucion(fechaInicio, fechaFin);
        return ResponseEntity.ok(prestamos);
    }
    
    @PostMapping
    public ResponseEntity<Prestamo> createPrestamo(@Valid @RequestBody Prestamo prestamo) {
        try {
            Prestamo nuevoPrestamo = prestamoService.save(prestamo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPrestamo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> crearPrestamo(
            @RequestParam Long usuarioId,
            @RequestParam Long libroId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDevolucion,
            @RequestParam(required = false) String observacion) {
        try {
            Prestamo prestamo = prestamoService.crearPrestamo(usuarioId, libroId, fechaDevolucion, observacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(prestamo);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                               .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                               .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("{\"error\": \"Error interno del servidor\"}");
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> updatePrestamo(@PathVariable Long id, @Valid @RequestBody Prestamo prestamo) {
        try {
            Optional<Prestamo> prestamoExistente = prestamoService.findById(id);
            if (prestamoExistente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            prestamo.setId(id);
            Prestamo prestamoActualizado = prestamoService.save(prestamo);
            return ResponseEntity.ok(prestamoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrestamo(@PathVariable Long id) {
        try {
            Optional<Prestamo> prestamo = prestamoService.findById(id);
            if (prestamo.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            prestamoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}