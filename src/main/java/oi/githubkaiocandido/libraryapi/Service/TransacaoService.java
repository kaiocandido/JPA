package oi.githubkaiocandido.libraryapi.Service;

import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.model.Autor;
import oi.githubkaiocandido.libraryapi.model.Generos;
import oi.githubkaiocandido.libraryapi.model.Livro;
import oi.githubkaiocandido.libraryapi.repository.AutorRepository;
import oi.githubkaiocandido.libraryapi.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private AutorRepository autorRepository;
    private LivroRepository livroRepository;

    @Transactional
    public void executar(){
        //SALVA O AUTOR
        Autor autor = new Autor();

        autor.setNome("Francisco");
        autor.setNascionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2002, 1, 27));
        autorRepository.save(autor);

        //SALVA O LIVRO
        Livro livro = new Livro();
        livro.setIsbn("6444446354");
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setGenero(Generos.CIENCIA);
        livro.setTitulo("outro livro");
        livro.setDataPublicacao(LocalDate.of(1990, 1 , 10));

        livro.setAutor(autor);
        livroRepository.save(livro);

        if (autor.getNome().equals("Jose")){
            throw new RuntimeException("RollBack!!");
        }

    }
}
