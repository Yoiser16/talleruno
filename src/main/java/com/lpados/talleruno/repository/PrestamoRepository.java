package com.lpados.talleruno.repository;

import com.lpados.talleruno.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    
    List<Prestamo> findByUsuarioId(Long usuarioId);
    
    List<Prestamo> findByLibroId(Long libroId);
    
    @Query("SELECT p FROM Prestamo p WHERE p.libro.id = :libroId AND p.fechaDevolucion >= CURRENT_DATE")
    Optional<Prestamo> findPrestamoActivoByLibroId(@Param("libroId") Long libroId);
    
    @Query("SELECT p FROM Prestamo p WHERE p.fechaDevolucion < CURRENT_DATE")
    List<Prestamo> findPrestamosVencidos();
    
    @Query("SELECT p FROM Prestamo p WHERE p.fechaDevolucion BETWEEN :fechaInicio AND :fechaFin")
    List<Prestamo> findPrestamosByFechaDevolucion(@Param("fechaInicio") LocalDate fechaInicio, 
                                                 @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT p FROM Prestamo p JOIN FETCH p.libro JOIN FETCH p.usuario ORDER BY p.fechaPrestamo DESC")
    List<Prestamo> findAllWithDetails();
}