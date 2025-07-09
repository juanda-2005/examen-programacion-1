package com.uninorte.proyecto_final_programacion_1.service;

import com.uninorte.proyecto_final_programacion_1.model.Producto;
import com.uninorte.proyecto_final_programacion_1.repository.ProductoRepository; // Importa el repositorio desde su paquete
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service // Esta anotación va en la clase Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Page<Producto> findPaginated(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }
}