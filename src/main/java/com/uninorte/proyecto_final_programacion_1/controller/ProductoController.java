package com.uninorte.proyecto_final_programacion_1.controller;

import com.uninorte.proyecto_final_programacion_1.model.Producto;
import com.uninorte.proyecto_final_programacion_1.service.CategoriaService;
import com.uninorte.proyecto_final_programacion_1.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listarProductos(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Producto> productoPage = productoService.findPaginated(pageable);

        model.addAttribute("productos", productoPage.getContent());
        model.addAttribute("currentPage", productoPage.getNumber());
        model.addAttribute("totalPages", productoPage.getTotalPages());
        model.addAttribute("totalItems", productoPage.getTotalElements());
        model.addAttribute("pageSize", size);

        int totalPages = productoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "productos/lista-productos"; // Apunta a templates/productos/lista-productos.html
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.findAll());
        return "productos/formulario-producto"; // Apunta a templates/productos/formulario-producto.html
    }

    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute("producto") Producto producto,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.findAll());
            return "productos/formulario-producto";
        }
        productoService.save(producto);
        redirectAttributes.addFlashAttribute("message", "Producto guardado exitosamente!");
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Producto> producto = productoService.findById(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            model.addAttribute("categorias", categoriaService.findAll());
            return "productos/formulario-producto";
        } else {
            redirectAttributes.addFlashAttribute("error", "Producto no encontrado.");
            return "redirect:/productos";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productoService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Producto eliminado exitosamente!");
        return "redirect:/productos";
    }
}