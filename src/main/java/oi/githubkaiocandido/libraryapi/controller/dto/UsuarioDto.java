package oi.githubkaiocandido.libraryapi.controller.dto;

import java.util.List;

public record UsuarioDto(String login, String password, List<String> roles) {
}
