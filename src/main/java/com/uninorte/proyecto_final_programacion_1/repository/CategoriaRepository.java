package com.uninorte.proyecto_final_programacion_1.repository;

import com.uninorte.proyecto_final_programacion_1.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // <--- ¡Asegúrate de que esta importación exista!

@Repository // <--- ¡Esta anotación es CRÍTICA!
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNombre(String nombre);
}