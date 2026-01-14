package oi.githubkaiocandido.libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Exceptions.RegistroDuplicadoException;
import oi.githubkaiocandido.libraryapi.Service.LivrosService;
import oi.githubkaiocandido.libraryapi.controller.dto.CadastroLivroDTO;
import oi.githubkaiocandido.libraryapi.controller.dto.ErroResposta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor

public class LivroController {
    private final LivrosService livrosService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto){
        try {

            return null;
        } catch (RegistroDuplicadoException e){
            var erroDTo = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTo.status()).body(erroDTo);
        }
    }
}
