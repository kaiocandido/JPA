package oi.githubkaiocandido.libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Exceptions.RegistroDuplicadoException;
import oi.githubkaiocandido.libraryapi.Service.LivrosService;
import oi.githubkaiocandido.libraryapi.controller.dto.CadastroLivroDTO;
import oi.githubkaiocandido.libraryapi.controller.dto.ErroResposta;
import oi.githubkaiocandido.libraryapi.controller.mappers.LivroMapper;
import oi.githubkaiocandido.libraryapi.model.Livro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor

public class LivroController implements GenericController {
    private final LivrosService livrosService;
    private final LivroMapper mapper;
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto){
        try {
            Livro livro = mapper.toEntity(dto);
            livrosService.salvar(livro);
            var url = gerarUrl(livro.getId());

            return  ResponseEntity.created(url).build();
        } catch (RegistroDuplicadoException e){
            var erroDTo = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTo.status()).body(erroDTo);
        }
    }
}
