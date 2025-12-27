package oi.githubkaiocandido.libraryapi.repository;

import oi.githubkaiocandido.libraryapi.model.Autor;
import oi.githubkaiocandido.libraryapi.model.Generos;
import oi.githubkaiocandido.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("6444446354");
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setGenero(Generos.CIENCIA);
        livro.setTitulo("Doidos");
        livro.setData_publicacao(LocalDate.of(1990, 1 , 10));

        Autor autor = autorRepository.findById(UUID.fromString("edea5550-b839-4a17-9646-3568b87f8078")).orElse(null);
        livro.setId_autor(autor);

        livroRepository.save(livro);
    }

    @Test
    public void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("6444446354");
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setGenero(Generos.CIENCIA);
        livro.setTitulo("outro livro");
        livro.setData_publicacao(LocalDate.of(1990, 1 , 10));

        Autor autor = new Autor();

        autor.setNome("Joana");
        autor.setNascionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2002, 01, 27));

        autorRepository.save(autor);
        livro.setId_autor(autor);
        livroRepository.save(livro);
    }


}