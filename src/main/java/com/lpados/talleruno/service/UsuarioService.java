package com.lpados.talleruno.service;

import com.lpados.talleruno.dto.UsuarioConPrestamosDTO;
import com.lpados.talleruno.entity.Usuario;
import com.lpados.talleruno.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Usuario> findAll() {
        return usuarioRepository.findAllOrderByNombre();
    }
    
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Optional<Usuario> findByIdentificacion(String identificacion) {
        return usuarioRepository.findByIdentificacion(identificacion);
    }
    
    public Optional<Usuario> findByCorreoElectronico(String correoElectronico) {
        return usuarioRepository.findByCorreoElectronico(correoElectronico);
    }
    
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    public List<Usuario> findByNombreContaining(String nombre) {
        return usuarioRepository.findByNombreCompletoContainingIgnoreCase(nombre);
    }
    
    @Transactional(readOnly = true)
    public List<UsuarioConPrestamosDTO> findAllUsuariosConPrestamos() {
        List<Usuario> usuarios = usuarioRepository.findAllOrderByNombre();
        
        return usuarios.stream().map(usuario -> {
            List<UsuarioConPrestamosDTO.PrestamoDetalleDTO> prestamosDTO = usuario.getPrestamos().stream()
                .map(prestamo -> new UsuarioConPrestamosDTO.PrestamoDetalleDTO(
                    prestamo.getId(),
                    prestamo.getLibro().getTitulo(),
                    prestamo.getLibro().getAutor(),
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucion(),
                    prestamo.getDiasRestantes(),
                    prestamo.getObservacion()
                )).collect(Collectors.toList());
            
            return new UsuarioConPrestamosDTO(
                usuario.getId(),
                usuario.getIdentificacion(),
                usuario.getTelefonoMovil(),
                usuario.getNombreCompleto(),
                usuario.getCorreoElectronico(),
                prestamosDTO
            );
        }).collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Optional<UsuarioConPrestamosDTO> findUsuarioConPrestamosById(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByIdSimple(id);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            List<UsuarioConPrestamosDTO.PrestamoDetalleDTO> prestamosDTO = usuario.getPrestamos().stream()
                .map(prestamo -> new UsuarioConPrestamosDTO.PrestamoDetalleDTO(
                    prestamo.getId(),
                    prestamo.getLibro().getTitulo(),
                    prestamo.getLibro().getAutor(),
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucion(),
                    prestamo.getDiasRestantes(),
                    prestamo.getObservacion()
                )).collect(Collectors.toList());
            
            return Optional.of(new UsuarioConPrestamosDTO(
                usuario.getId(),
                usuario.getIdentificacion(),
                usuario.getTelefonoMovil(),
                usuario.getNombreCompleto(),
                usuario.getCorreoElectronico(),
                prestamosDTO
            ));
        }
        
        return Optional.empty();
    }
}