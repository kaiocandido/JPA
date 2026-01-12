package oi.githubkaiocandido.libraryapi.validator;

import oi.githubkaiocandido.libraryapi.Exceptions.RegistroDuplicadoException;
import oi.githubkaiocandido.libraryapi.model.Autor;
import oi.githubkaiocandido.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private final AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor){
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }


    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorExiste = autorRepository.findByNomeAndDataNascimentoAndNascionalidade(autor.getNome(), autor.getDataNascimento(), autor.getNascionalidade());

        if (autor.getId() == null){
            return autorExiste.isPresent();
        }

        return !autor.getId().equals(autorExiste.get().getId()) && autorExiste.isPresent(); }
}
