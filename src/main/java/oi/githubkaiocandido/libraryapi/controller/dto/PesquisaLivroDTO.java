package oi.githubkaiocandido.libraryapi.controller.dto;

import oi.githubkaiocandido.libraryapi.model.Generos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PesquisaLivroDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        Generos genero,
        BigDecimal preco,
        AutorDTO autor
) {

}
