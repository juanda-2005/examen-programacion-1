package com.uninorte.proyecto_final_programacion_1.controller;

import com.uninorte.proyecto_final_programacion_1.model.Usuario;
import com.uninorte.proyecto_final_programacion_1.service.CustomUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth") // Añadimos un prefijo para las rutas de autenticación, es buena práctica
public class AuthController {

    private final CustomUserDetailsService userService;

    public AuthController(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/register"; // Apunta a templates/auth/register.html
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("usuario") Usuario usuario,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "auth/register";
        }
        if (userService.findByUsername(usuario.getUsername()).isPresent()) {
            result.rejectValue("username", "error.usuario", "El nombre de usuario ya existe.");
            return "auth/register";
        }
        userService.saveUser(usuario);
        redirectAttributes.addFlashAttribute("message", "¡Registro exitoso! Por favor, inicia sesión.");
        return "redirect:/login"; // <-- Puedes cambiar esto a "/productos" si quieres ir directo, pero /login es más común después de un registro exitoso para que el usuario ponga su nueva contraseña.
        // Si quieres que vaya directo a productos:
        // return "redirect:/productos";
    }

    // Nota: El mapping para /login suele manejarse directamente por Spring Security y no necesita un controlador explícito a menos que quieras lógica adicional.
    // La página de login se sirve a través de SecurityConfig.loginPage("/login")
}