package com.uninorte.proyecto_final_programacion_1.repository;

import com.uninorte.proyecto_final_programacion_1.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Puedes añadir métodos de búsqueda personalizados aquí si los necesitas
}