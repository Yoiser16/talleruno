package com.lpados.talleruno.service;

import com.lpados.talleruno.entity.Libro;
import com.lpados.talleruno.entity.Prestamo;
import com.lpados.talleruno.entity.Usuario;
import com.lpados.talleruno.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrestamoService {
    
    @Autowired
    private PrestamoRepository prestamoRepository;
    
    @Autowired
    private LibroService libroService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    public List<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }
    
    public Optional<Prestamo> findById(Long id) {
        return prestamoRepository.findById(id);
    }
    
    public List<Prestamo> findByUsuarioId(Long usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId);
    }
    
    public List<Prestamo> findByLibroId(Long libroId) {
        return prestamoRepository.findByLibroId(libroId);
    }
    
    public Prestamo save(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }
    
    public void deleteById(Long id) {
        prestamoRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public boolean isLibroDisponible(Long libroId) {
        Optional<Prestamo> prestamoActivo = prestamoRepository.findPrestamoActivoByLibroId(libroId);
        return prestamoActivo.isEmpty();
    }
    
    public Prestamo crearPrestamo(Long usuarioId, Long libroId, LocalDate fechaDevolucion, String observacion) {

        if (!isLibroDisponible(libroId)) {
            throw new IllegalStateException("El libro no está disponible para préstamo");
        }
        

        Optional<Usuario> usuarioOpt = usuarioService.findById(usuarioId);
        Optional<Libro> libroOpt = libroService.findById(libroId);
        
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        
        if (libroOpt.isEmpty()) {
            throw new IllegalArgumentException("Libro no encontrado");
        }
        
    Prestamo prestamo = new Prestamo();
    prestamo.setUsuario(usuarioOpt.get());
    prestamo.setLibro(libroOpt.get());
    java.time.ZoneId zonaColombia = java.time.ZoneId.of("America/Bogota");
    java.time.LocalDate fechaPrestamoColombia = java.time.ZonedDateTime.now(zonaColombia).toLocalDate();
    prestamo.setFechaPrestamo(fechaPrestamoColombia);
    prestamo.setFechaDevolucion(fechaDevolucion);
    prestamo.setObservacion(observacion);
        
    return save(prestamo);
    }
    
    @Transactional(readOnly = true)
    public List<Prestamo> findPrestamosVencidos() {
        return prestamoRepository.findPrestamosVencidos();
    }
    
    @Transactional(readOnly = true)
    public List<Prestamo> findPrestamosByFechaDevolucion(LocalDate fechaInicio, LocalDate fechaFin) {
        return prestamoRepository.findPrestamosByFechaDevolucion(fechaInicio, fechaFin);
    }
    
    @Transactional(readOnly = true)
    public List<Prestamo> findAllWithDetails() {
        return prestamoRepository.findAllWithDetails();
    }
}