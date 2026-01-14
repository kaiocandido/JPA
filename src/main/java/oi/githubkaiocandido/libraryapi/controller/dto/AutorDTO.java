package oi.githubkaiocandido.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import oi.githubkaiocandido.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "Campo Obrigatorio")
        @Size(max = 100, min = 3, message = "O campo esta fora do padrão")
        String nome,
        @NotNull(message = "Campo Obrigatorio")
        @Past(message = "Não pode ser uma data futura")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo Obrigatorio")
        @Size(max = 50, min = 5, message = "O campo esta fora do padrão")
        String nascionalidade
) {

}
