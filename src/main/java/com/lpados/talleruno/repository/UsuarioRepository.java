package com.lpados.talleruno.repository;

import com.lpados.talleruno.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByIdentificacion(String identificacion);
    
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);
    
    List<Usuario> findByNombreCompletoContainingIgnoreCase(String nombre);
    
    @Query("SELECT u FROM Usuario u ORDER BY u.nombreCompleto")
    List<Usuario> findAllOrderByNombre();
    
    @Query("SELECT u FROM Usuario u WHERE u.id = :id")
    Optional<Usuario> findByIdSimple(@Param("id") Long id);
}