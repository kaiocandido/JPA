package oi.githubkaiocandido.libraryapi.Service;

import oi.githubkaiocandido.libraryapi.model.Autor;
import oi.githubkaiocandido.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository){
        this.autorRepository = autorRepository;
    }

    public Autor salvar(Autor autor){
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterId(UUID id){
        return autorRepository.findById(id);
    }

    public void deletar(Autor id){
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
        autorRepository.save(autor);
    }
}
