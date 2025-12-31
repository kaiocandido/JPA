package oi.githubkaiocandido.libraryapi.repository;

import oi.githubkaiocandido.libraryapi.model.Autor;
import oi.githubkaiocandido.libraryapi.model.Livro;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
    //Query methods
    List<Livro> findByAutor(Autor autor);

    //buscar livro por TITULO
    List<Livro> findByTitulo(String titulo);

    //buscar livro por ISBN
    List<Livro> findByIsbn(String isbn);

    //buscar livro por TITULO e PRECO
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    //buscar livro por TITULO ou ISBN
    List<Livro> findByTituloOrIsbn(String titulo, String preco);

    //buscar livro por Data inicio e fim
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    //buscar livro por palavra
    List<Livro> findByDataTituloLike(String palavra);

    //Ordenar livros pelo titulo
    List<Livro> findByTituluOrIsbnOrdeByTitulo(String titulo, String isbn);

}
