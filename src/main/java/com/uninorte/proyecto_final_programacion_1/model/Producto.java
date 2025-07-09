package com.uninorte.proyecto_final_programacion_1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres.")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres.")
    private String descripcion;

    @NotNull(message = "El precio no puede estar vacío.")
    @Min(value = 0, message = "El precio debe ser un valor positivo.")
    private Double precio;

    @NotNull(message = "El stock no puede estar vacío.")
    @Min(value = 0, message = "El stock debe ser un valor no negativo.")
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @NotNull(message = "Debe seleccionar una categoría.")
    private Categoria categoria;
}