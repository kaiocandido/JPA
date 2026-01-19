package oi.githubkaiocandido.libraryapi.validator;

import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Exceptions.RegistroDuplicadoException;
import oi.githubkaiocandido.libraryapi.model.Livro;
import oi.githubkaiocandido.libraryapi.repository.LivroRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {
    private final LivroRepository repository;

    public void validar(Livro livro){
        if (existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("Já existe com ISBN cadastrado");
        }
    }

    public boolean existeLivroComIsbn(Livro livro){
        Optional<Livro> byIsbnUnico = repository.findByIsbnUnico(livro.getIsbn());

        if (livro.getId() == null){
            return byIsbnUnico.isPresent();
        }

        return byIsbnUnico.map(Livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));
    }
}
