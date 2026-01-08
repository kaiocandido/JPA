package oi.githubkaiocandido.libraryapi.controller.dto;

import oi.githubkaiocandido.libraryapi.model.Autor;

import java.time.LocalDate;

public record AutorDTO(String nome, LocalDate dataNascimento, String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setDataNascimento(dataNascimento);
        autor.setNascionalidade(nacionalidade);
        return autor;
    }
}
