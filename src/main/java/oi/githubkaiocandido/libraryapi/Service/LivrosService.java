package oi.githubkaiocandido.libraryapi.Service;

import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Exceptions.OperacaoNaoPermitidaException;
import oi.githubkaiocandido.libraryapi.model.Generos;
import oi.githubkaiocandido.libraryapi.model.Livro;
import oi.githubkaiocandido.libraryapi.model.Usuario;
import oi.githubkaiocandido.libraryapi.repository.LivroRepository;
import oi.githubkaiocandido.libraryapi.repository.specs.LivroSpecs;
import oi.githubkaiocandido.libraryapi.validator.LivroValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import oi.githubkaiocandido.libraryapi.security.SecurityService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivrosService {

    private final LivroRepository  livroRepository;
    private final LivroValidator validator;
    private final SecurityService securityService;

    public Livro salvar(Livro livro){
        validator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterId(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro){
        livroRepository.delete(livro);
    }

    public Page<Livro> buscaPorLivros(
            String isbn,
            String titulo,
            String nomeAutor,
            Generos genero,
            Integer anoPublicacao,
            Integer pagina,
            Integer tamanhoPagina
    ) {

        Specification<Livro> specs = Specification.where(
                (root, query, criteriaBuilder) -> criteriaBuilder.conjunction()
        );

        if (isbn != null) {
            specs = specs.and(LivroSpecs.isbnEqual(isbn));
        }
        if (titulo != null) {
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }
        if (genero != null) {
            specs = specs.and(LivroSpecs.generoEqual(genero));
        }
        if (anoPublicacao != null) {
            specs = specs.and(LivroSpecs.anoEqual(anoPublicacao));
        }
        if (nomeAutor != null) {
            specs = specs.and(LivroSpecs.nomeAutorLike(nomeAutor));
        }

        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);

        return livroRepository.findAll(specs, pageable);
    }

    public void atualiza(Livro livro) {

        if (livro.getId() == null){
            throw  new OperacaoNaoPermitidaException("Esse usuario possui livros cadastrados");
        }

        validator.validar(livro);
        livroRepository.save(livro);
    }
}
