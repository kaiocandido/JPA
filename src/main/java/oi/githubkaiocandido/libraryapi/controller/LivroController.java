package oi.githubkaiocandido.libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Exceptions.RegistroDuplicadoException;
import oi.githubkaiocandido.libraryapi.Service.LivrosService;
import oi.githubkaiocandido.libraryapi.controller.dto.CadastroLivroDTO;
import oi.githubkaiocandido.libraryapi.controller.dto.ErroResposta;
import oi.githubkaiocandido.libraryapi.controller.dto.PesquisaLivroDTO;
import oi.githubkaiocandido.libraryapi.controller.mappers.LivroMapper;
import oi.githubkaiocandido.libraryapi.model.Generos;
import oi.githubkaiocandido.libraryapi.model.Livro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor

public class LivroController implements GenericController {
    private final LivrosService livrosService;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {
            Livro livro = mapper.toEntity(dto);
            livrosService.salvar(livro);
            var url = gerarUrl(livro.getId());

            return ResponseEntity.created(url).build();
        } catch (RegistroDuplicadoException e) {
            var erroDTo = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTo.status()).body(erroDTo);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<PesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id ){
        return livrosService.obterId(UUID.fromString(id))
                .map(livro ->
                {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        return livrosService.obterId(UUID.fromString(id)).map(livro -> {
            livrosService.deletar(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PesquisaLivroDTO>> pesquisaAll(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            Generos genero,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao
    ){
        var resultado = livrosService.buscaPorLivros(isbn,  titulo,  nomeAutor,  genero,  anoPublicacao);
        var lista = resultado.stream().map(mapper::toDTO).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizarLivro(@PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO dto){
        return livrosService.obterId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidade = mapper.toEntity(dto);
                    livro.setDataPublicacao(entidade.getDataPublicacao());
                    livro.setIsbn(entidade.getIsbn());
                    livro.setPreco(entidade.getPreco());
                    livro.setGenero(entidade.getGenero());
                    livro.setTitulo(entidade.getTitulo());
                    livro.setAutor(entidade.getAutor());

                    livrosService.atualiza(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
