package oi.githubkaiocandido.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import oi.githubkaiocandido.libraryapi.model.Generos;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @NotBlank(message = "Campo Obrigatorio!!")
        @ISBN
        String isbn,
        @NotBlank(message = "Campo Obrigatorio!!")
        String titulo,
        @NotNull(message = "Campo Obrigatorio!!")
        @Past(message = "Não pode ser uma data futura")
        LocalDate dataPublicacao,
        Generos genero,
        BigDecimal preco,
        @NotNull(message = "Campo Obrigatorio!!")
        UUID idAutor
        ) {

}
