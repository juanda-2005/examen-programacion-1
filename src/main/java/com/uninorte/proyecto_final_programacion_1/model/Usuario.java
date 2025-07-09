package com.uninorte.proyecto_final_programacion_1.model;

import jakarta.persistence.*;
import lombok.Data; // Requiere Lombok
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Genera constructor sin argumentos
@AllArgsConstructor // Genera constructor con todos los argumentos
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // ¡Se almacenará encriptada!

    @Column(unique = true, nullable = false)
    private String email;

    // Puedes usar un String simple o una entidad separada para roles.
    // Para simplificar, lo mantendremos como String.
    private String roles; // Ejemplo: "ROLE_USER,ROLE_ADMIN"
}