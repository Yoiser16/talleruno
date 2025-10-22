package com.lpados.talleruno.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El ISBN es obligatorio")
    @Size(max = 50)
    @Column(name = "isbn", nullable = false, unique = true, length = 50)
    private String isbn;
    
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100)
    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;
    
    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 100)
    @Column(name = "autor", nullable = false, length = 100)
    private String autor;
    
    @NotBlank(message = "La editorial es obligatoria")
    @Size(max = 50)
    @Column(name = "editorial", nullable = false, length = 50)
    private String editorial;
    
    @Column(name = "anio")
    private Integer anio;
    
    @Size(max = 30)
    @Column(name = "edicion", length = 30)
    private String edicion;
    
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("libro")
    private List<Prestamo> prestamos = new ArrayList<>();
    
    public Libro() {}
    
    public Libro(String isbn, String titulo, String autor, String editorial, Integer anio, String edicion) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.edicion = edicion;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public String getEditorial() {
        return editorial;
    }
    
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    
    public Integer getAnio() {
        return anio;
    }
    
    public void setAnio(Integer anio) {
        this.anio = anio;
    }
    
    public String getEdicion() {
        return edicion;
    }
    
    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }
    
    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
    
    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
    
    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", editorial='" + editorial + '\'' +
                ", anio=" + anio +
                ", edicion='" + edicion + '\'' +
                '}';
    }
}