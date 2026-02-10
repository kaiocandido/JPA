package oi.githubkaiocandido.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
@Schema(name = "Usuario")
public record UsuarioDto(
        @NotBlank(message = "login Obrigatorio") String login,
        @Email(message = "Email Invalido")
        @NotBlank(message = "Email Invalido")  String email,
        @NotBlank(message = "Senha Obrigatorio") String password,
        List<String> roles) {
}
