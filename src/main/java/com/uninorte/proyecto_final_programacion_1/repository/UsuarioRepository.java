package com.uninorte.proyecto_final_programacion_1.repository;

import com.uninorte.proyecto_final_programacion_1.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username); // Método para buscar un usuario por su nombre de usuario
    boolean existsByUsername(String username); // Verificar si un username ya existe
    boolean existsByEmail(String email); // Verificar si un email ya existe
}