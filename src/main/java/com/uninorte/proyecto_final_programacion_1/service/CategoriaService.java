package com.uninorte.proyecto_final_programacion_1.service;

import com.uninorte.proyecto_final_programacion_1.model.Categoria;
import com.uninorte.proyecto_final_programacion_1.repository.CategoriaRepository; // Importa el repositorio desde su paquete
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service // Esta anotación va en la clase Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Page<Categoria> findPaginated(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }

    public boolean existsByNombre(String nombre) {
        return categoriaRepository.existsByNombre(nombre);
    }
}