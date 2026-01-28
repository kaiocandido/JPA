package oi.githubkaiocandido.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDto(
        @NotBlank(message = "login Obrigatorio") String login,
        @Email(message = "Email Invalido")
        @NotBlank(message = "Email Invalido")  String email,
        @NotBlank(message = "Senha Obrigatorio") String password,
        List<String> roles) {
}
