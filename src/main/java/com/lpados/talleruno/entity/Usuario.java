package com.lpados.talleruno.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "La identificación es obligatoria")
    @Size(max = 20)
    @Column(name = "identificacion", nullable = false, unique = true, length = 20)
    private String identificacion;
    
    @NotBlank(message = "El teléfono móvil es obligatorio")
    @Size(max = 15)
    @Column(name = "telefono_movil", nullable = false, length = 15)
    private String telefonoMovil;
    
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 80)
    @Column(name = "nombre_completo", nullable = false, length = 80)
    private String nombreCompleto;
    
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe tener un formato válido")
    @Size(max = 100)
    @Column(name = "correo_electronico", nullable = false, unique = true, length = 100)
    private String correoElectronico;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("usuario")
    private List<Prestamo> prestamos = new ArrayList<>();
    
    public Usuario() {}
    
    public Usuario(String identificacion, String telefonoMovil, String nombreCompleto, String correoElectronico) {
        this.identificacion = identificacion;
        this.telefonoMovil = telefonoMovil;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
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
    
    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
    
    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", identificacion='" + identificacion + '\'' +
                ", telefonoMovil='" + telefonoMovil + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                '}';
    }
}