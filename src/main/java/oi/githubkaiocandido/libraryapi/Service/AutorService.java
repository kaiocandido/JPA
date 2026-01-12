package oi.githubkaiocandido.libraryapi.Service;

import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Exceptions.OperacaoNaoPermitidaException;
import oi.githubkaiocandido.libraryapi.model.Autor;
import oi.githubkaiocandido.libraryapi.repository.AutorRepository;
import oi.githubkaiocandido.libraryapi.repository.LivroRepository;
import oi.githubkaiocandido.libraryapi.validator.AutorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final LivroRepository livroRepository;

    private final AutorRepository autorRepository;

    private  final AutorValidator validator;


    public Autor salvar(Autor autor){
        validator.validar(autor);
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterId(UUID id){
        return autorRepository.findById(id);
    }

    public void deletar(Autor id){

        if (possuiLivro(id)){
            throw  new OperacaoNaoPermitidaException("Esse usuario possui livros cadastrados");
        }
        autorRepository.delete(id);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade){
        if (nome != null && nacionalidade != null){
            return autorRepository.findByNomeContainingIgnoreCaseAndNascionalidadeContainingIgnoreCase(nome, nacionalidade);
        }else if (nome != null){
           return autorRepository.findByNomeContainingIgnoreCase(nome);
        }else if (nacionalidade != null){
            return autorRepository.findByNascionalidadeContainingIgnoreCase(nacionalidade);
        }

        return autorRepository.findAll();
    }

    public void atualizar(Autor autor){

        if (autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessario que o autor esteja cadastrado!!");
        }
        validator.validar(autor);
        autorRepository.save(autor);
    }

    public boolean possuiLivro(Autor autor){
       return livroRepository.existsByAutor(autor);
    }
}
