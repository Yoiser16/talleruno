package com.lpados.talleruno.controller;

import com.lpados.talleruno.dto.UsuarioConPrestamosDTO;
import com.lpados.talleruno.entity.Usuario;
import com.lpados.talleruno.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<Usuario> getUsuarioByIdentificacion(@PathVariable String identificacion) {
        Optional<Usuario> usuario = usuarioService.findByIdentificacion(identificacion);
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/con-prestamos")
    public ResponseEntity<List<UsuarioConPrestamosDTO>> getAllUsuariosConPrestamos() {
        List<UsuarioConPrestamosDTO> usuarios = usuarioService.findAllUsuariosConPrestamos();
        return ResponseEntity.ok(usuarios);
    }
    
    @GetMapping("/{id}/con-prestamos")
    public ResponseEntity<UsuarioConPrestamosDTO> getUsuarioConPrestamosById(@PathVariable Long id) {
        Optional<UsuarioConPrestamosDTO> usuario = usuarioService.findUsuarioConPrestamosById(id);
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscarUsuariosPorNombre(@RequestParam String nombre) {
        List<Usuario> usuarios = usuarioService.findByNombreContaining(nombre);
        return ResponseEntity.ok(usuarios);
    }
    
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            Optional<Usuario> usuarioExistente = usuarioService.findByIdentificacion(usuario.getIdentificacion());
            if (usuarioExistente.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            Usuario nuevoUsuario = usuarioService.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        try {
            Optional<Usuario> usuarioExistente = usuarioService.findById(id);
            if (usuarioExistente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            usuario.setId(id);
            Usuario usuarioActualizado = usuarioService.save(usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        try {
            Optional<Usuario> usuario = usuarioService.findById(id);
            if (usuario.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}