package com.lpados.talleruno.dto;

import java.time.LocalDate;
import java.util.List;

public class UsuarioConPrestamosDTO {
    
    private Long id;
    private String identificacion;
    private String telefonoMovil;
    private String nombreCompleto;
    private String correoElectronico;
    private List<PrestamoDetalleDTO> prestamos;
    
    public UsuarioConPrestamosDTO() {}
    
    public UsuarioConPrestamosDTO(Long id, String identificacion, String telefonoMovil, 
                                 String nombreCompleto, String correoElectronico, 
                                 List<PrestamoDetalleDTO> prestamos) {
        this.id = id;
        this.identificacion = identificacion;
        this.telefonoMovil = telefonoMovil;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.prestamos = prestamos;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getIdentificacion() {
        return identificacion;
    }
    
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    public String getTelefonoMovil() {
        return telefonoMovil;
    }
    
    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String getCorreoElectronico() {
        return correoElectronico;
    }
    
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    
    public List<PrestamoDetalleDTO> getPrestamos() {
        return prestamos;
    }
    
    public void setPrestamos(List<PrestamoDetalleDTO> prestamos) {
        this.prestamos = prestamos;
    }
    
    public static class PrestamoDetalleDTO {
        private Long prestamoId;
        private String tituloLibro;
        private String autorLibro;
        private LocalDate fechaPrestamo;
        private LocalDate fechaDevolucion;
        private long diasRestantes;
        private String observacion;
        
        public PrestamoDetalleDTO() {}
        
        public PrestamoDetalleDTO(Long prestamoId, String tituloLibro, String autorLibro,
                                LocalDate fechaPrestamo, LocalDate fechaDevolucion, 
                                long diasRestantes, String observacion) {
            this.prestamoId = prestamoId;
            this.tituloLibro = tituloLibro;
            this.autorLibro = autorLibro;
            this.fechaPrestamo = fechaPrestamo;
            this.fechaDevolucion = fechaDevolucion;
            this.diasRestantes = diasRestantes;
            this.observacion = observacion;
        }
        
        public Long getPrestamoId() {
            return prestamoId;
        }
        
        public void setPrestamoId(Long prestamoId) {
            this.prestamoId = prestamoId;
        }
        
        public String getTituloLibro() {
            return tituloLibro;
        }
        
        public void setTituloLibro(String tituloLibro) {
            this.tituloLibro = tituloLibro;
        }
        
        public String getAutorLibro() {
            return autorLibro;
        }
        
        public void setAutorLibro(String autorLibro) {
            this.autorLibro = autorLibro;
        }
        
        public LocalDate getFechaPrestamo() {
            return fechaPrestamo;
        }
        
        public void setFechaPrestamo(LocalDate fechaPrestamo) {
            this.fechaPrestamo = fechaPrestamo;
        }
        
        public LocalDate getFechaDevolucion() {
            return fechaDevolucion;
        }
        
        public void setFechaDevolucion(LocalDate fechaDevolucion) {
            this.fechaDevolucion = fechaDevolucion;
        }
        
        public long getDiasRestantes() {
            return diasRestantes;
        }
        
        public void setDiasRestantes(long diasRestantes) {
            this.diasRestantes = diasRestantes;
        }
        
        public String getObservacion() {
            return observacion;
        }
        
        public void setObservacion(String observacion) {
            this.observacion = observacion;
        }
    }
}