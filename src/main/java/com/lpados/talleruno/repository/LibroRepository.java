package com.lpados.talleruno.repository;

import com.lpados.talleruno.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    Optional<Libro> findByIsbn(String isbn);
    
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    
    List<Libro> findByAutorContainingIgnoreCase(String autor);
    
    @Query("SELECT l FROM Libro l WHERE l.id NOT IN " +
           "(SELECT DISTINCT p.libro.id FROM Prestamo p WHERE p.fechaDevolucion >= CURRENT_DATE) " +
           "ORDER BY l.titulo")
    List<Libro> findLibrosDisponibles();
    
    @Query("SELECT DISTINCT l FROM Libro l JOIN l.prestamos p WHERE p.fechaDevolucion >= CURRENT_DATE " +
           "ORDER BY l.titulo")
    List<Libro> findLibrosPrestados();
    
    @Query("SELECT l FROM Libro l ORDER BY l.titulo")
    List<Libro> findAllOrderByTitulo();
}