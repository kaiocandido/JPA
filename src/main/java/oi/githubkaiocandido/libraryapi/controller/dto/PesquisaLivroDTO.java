package oi.githubkaiocandido.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import oi.githubkaiocandido.libraryapi.model.Generos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
@Schema(name = "PesquisaLivro")
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
