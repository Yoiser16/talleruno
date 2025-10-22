package com.lpados.talleruno.service;

import com.lpados.talleruno.entity.Libro;
import com.lpados.talleruno.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LibroService {
    
    @Autowired
    private LibroRepository libroRepository;
    
    public List<Libro> findAll() {
        return libroRepository.findAllOrderByTitulo();
    }
    
    public Optional<Libro> findById(Long id) {
        return libroRepository.findById(id);
    }
    
    public Optional<Libro> findByIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn);
    }
    
    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }
    
    public void deleteById(Long id) {
        libroRepository.deleteById(id);
    }
    
    public List<Libro> findByTituloContaining(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }
    
    public List<Libro> findByAutorContaining(String autor) {
        return libroRepository.findByAutorContainingIgnoreCase(autor);
    }
    
    @Transactional(readOnly = true)
    public List<Libro> findLibrosDisponibles() {
        return libroRepository.findLibrosDisponibles();
    }
    
    @Transactional(readOnly = true)
    public List<Libro> findLibrosPrestados() {
        return libroRepository.findLibrosPrestados();
    }
}