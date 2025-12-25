package oi.githubkaiocandido.libraryapi.repoitory;

import oi.githubkaiocandido.libraryapi.model.Autor;
import oi.githubkaiocandido.libraryapi.repository.AutorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNascionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1991, 01, 27));

        var autorSalvo = repository.save(autor);

        System.out.println("Autor Salvo: " + autorSalvo.getId());
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("ec1f49d7-5ac9-4a77-a128-2d645cb847a4");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor: ");
            System.out.println(autorEncontrado);
            autorEncontrado.setDataNascimento(LocalDate.of(1997, 01, 27));

            repository.save(autorEncontrado);
        }

    }
}
