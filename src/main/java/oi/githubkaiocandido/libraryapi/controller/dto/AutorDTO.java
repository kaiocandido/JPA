package oi.githubkaiocandido.libraryapi.controller.dto;

import oi.githubkaiocandido.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(UUID id, String nome, LocalDate dataNascimento, String nascionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setDataNascimento(dataNascimento);
        autor.setNascionalidade(nascionalidade);
        return autor;
    }
}
