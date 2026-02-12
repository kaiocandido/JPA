package oi.githubkaiocandido.libraryapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oi.githubkaiocandido.libraryapi.Exceptions.RegistroDuplicadoException;
import oi.githubkaiocandido.libraryapi.Service.LivrosService;
import oi.githubkaiocandido.libraryapi.controller.dto.CadastroLivroDTO;
import oi.githubkaiocandido.libraryapi.controller.dto.ErroResposta;
import oi.githubkaiocandido.libraryapi.controller.dto.PesquisaLivroDTO;
import oi.githubkaiocandido.libraryapi.controller.mappers.LivroMapper;
import oi.githubkaiocandido.libraryapi.model.Generos;
import oi.githubkaiocandido.libraryapi.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
@Tag(name = "Livros")
@Slf4j
public class LivroController implements GenericController {
    private final LivrosService livrosService;
    private final LivroMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Salvar Livros", description = "Salvar Livros")
    @ApiResponses({
            @ApiResponse(responseCode =  "201", description = "Livro cadastrado com sucesso"),
            @ApiResponse(responseCode =  "422", description = "Erro de validação"),
            @ApiResponse(responseCode =  "409", description = "Livro já cadastrado")
    })

    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        log.info("Cadastrando novo livro {}", dto.titulo());
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
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar Livros", description = "Pesquisar Livros por UUID")
    @ApiResponses({
            @ApiResponse(responseCode =  "201", description = "Livro encontrado com sucesso"),
            @ApiResponse(responseCode =  "404", description = "Livro não encotrado")
    })
    public ResponseEntity<PesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id ){
        return livrosService.obterId(UUID.fromString(id))
                .map(livro ->
                {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Deletar Livros", description = "Deletar Livros por UUID")
    @ApiResponses({
            @ApiResponse(responseCode =  "201", description = "Livro deletado com sucesso"),
            @ApiResponse(responseCode =  "404", description = "Livro não encotrado")
    })
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        log.info("Livro deletado id: {}", id);
        return livrosService.obterId(UUID.fromString(id)).map(livro -> {
            livrosService.deletar(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Listar todos Livros", description = "Listar todos Livros cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode =  "201", description = "Livro listado com sucesso"),
            @ApiResponse(responseCode =  "404", description = "Livro não encotrado")
    })
    public ResponseEntity<org.springframework.data.domain.Page<PesquisaLivroDTO>> pesquisaAll(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "nome-autor", required = false) String nomeAutor,
            @RequestParam(value = "genero", required = false) Generos genero,
            @RequestParam(value = "ano-publicacao", required = false) Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina
    ) {

        org.springframework.data.domain.Page<Livro> resultado =
                livrosService.buscaPorLivros(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);

        Page<PesquisaLivroDTO> pageDto = resultado.map(mapper::toDTO);

        return ResponseEntity.ok(pageDto);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Atualizar Livros", description = "Atualizar Livros por UUID")
    @ApiResponses({
            @ApiResponse(responseCode =  "204", description = "Livro atualizado com sucesso"),
            @ApiResponse(responseCode =  "404", description = "Livro não encotrado"),
            @ApiResponse(responseCode =  "409", description = "Livro já cadastrado")
    })
    public ResponseEntity<Object> atualizarLivro(@PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO dto){
        log.info("Livro atualizado {}", dto.titulo());
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
