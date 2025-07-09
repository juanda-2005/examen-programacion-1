package com.uninorte.proyecto_final_programacion_1.controller;

import com.uninorte.proyecto_final_programacion_1.model.Categoria;
import com.uninorte.proyecto_final_programacion_1.service.CategoriaService;
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
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listarCategorias(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Categoria> categoriaPage = categoriaService.findPaginated(pageable);

        model.addAttribute("categorias", categoriaPage.getContent());
        model.addAttribute("currentPage", categoriaPage.getNumber());
        model.addAttribute("totalPages", categoriaPage.getTotalPages());
        model.addAttribute("totalItems", categoriaPage.getTotalElements());
        model.addAttribute("pageSize", size);

        int totalPages = categoriaPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "categorias/lista-categorias"; // Apunta a templates/categorias/lista-categorias.html
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/formulario-categoria"; // Apunta a templates/categorias/formulario-categoria.html
    }

    @PostMapping("/guardar")
    public String guardarCategoria(@Valid @ModelAttribute("categoria") Categoria categoria,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "categorias/formulario-categoria";
        }
        if (categoriaService.existsByNombre(categoria.getNombre()) && categoria.getId() == null) {
            result.rejectValue("nombre", "error.categoria", "La categoría con este nombre ya existe.");
            return "categorias/formulario-categoria";
        }
        categoriaService.save(categoria);
        redirectAttributes.addFlashAttribute("message", "Categoría guardada exitosamente!");
        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Categoria> categoria = categoriaService.findById(id);
        if (categoria.isPresent()) {
            model.addAttribute("categoria", categoria.get());
            return "categorias/formulario-categoria";
        } else {
            redirectAttributes.addFlashAttribute("error", "Categoría no encontrada.");
            return "redirect:/categorias";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Considerar añadir lógica para verificar si hay productos asociados antes de eliminar
        categoriaService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Categoría eliminada exitosamente!");
        return "redirect:/categorias";
    }
}