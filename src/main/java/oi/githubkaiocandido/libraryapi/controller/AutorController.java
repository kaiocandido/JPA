package oi.githubkaiocandido.libraryapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oi.githubkaiocandido.libraryapi.Exceptions.OperacaoNaoPermitidaException;
import oi.githubkaiocandido.libraryapi.Exceptions.RegistroDuplicadoException;
import oi.githubkaiocandido.libraryapi.Service.AutorService;
import oi.githubkaiocandido.libraryapi.Service.UsuariosService;
import oi.githubkaiocandido.libraryapi.controller.dto.AutorDTO;
import oi.githubkaiocandido.libraryapi.controller.dto.ErroResposta;
import oi.githubkaiocandido.libraryapi.controller.mappers.AutorMapper;
import oi.githubkaiocandido.libraryapi.model.Autor;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores") // -> host http:localhost:8080/autores
@RequiredArgsConstructor
@Tag(name = "Autores")
@Slf4j //habilita logs
public class AutorController implements GenericController {


    private final AutorService autorService;
    private final AutorMapper mapper;
    private final UsuariosService usuariosSerive;

    @PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar Autores", description = "Cadastrar novo autor")
    @ApiResponses({
            @ApiResponse(responseCode =  "201", description = "Cadatrado com sucesso"),
            @ApiResponse(responseCode =  "422", description = "Erro de validação"),
            @ApiResponse(responseCode =  "409", description = "Autor já cadastrado")
    })
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autor) {
        log.info("Cadastrando novo autor: {}", autor.nome());

        try {
            var autorEntidade = mapper.toEntity(autor);
            autorService.salvar(autorEntidade);

            var url = gerarUrl(autorEntidade.getId());


            return ResponseEntity.created(gerarUrl(autorEntidade.getId())).build();
        } catch (RegistroDuplicadoException e) {
            var erroDTo = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTo.status()).body(erroDTo);
        }
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Obter detalhes de Autores", description = "Retorna os dados do autor pelo UUID")
    @ApiResponses({
            @ApiResponse(responseCode =  "201", description = "Autor encontrado com sucesso"),
            @ApiResponse(responseCode =  "404", description = "Autor não encotrado")
    })
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        Optional<Autor> autor = autorService.obterId(idAutor);
        if (autor.isPresent()) {
            Autor entidade = autor.get();
            AutorDTO dto = mapper.toDTO(entidade);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Deletar Autores", description = "Deletar autores pelo UUID")
    @ApiResponses({
            @ApiResponse(responseCode =  "204", description = "Autor deletado com sucesso"),
            @ApiResponse(responseCode =  "404", description = "Autor não encotrado"),
            @ApiResponse(responseCode =  "400", description = "Autor possui livro cadastrado")
    })
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        log.info("Deletando autor deID: {}", id);
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autor = autorService.obterId(idAutor);

            if (autor.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            autorService.deletar(autor.get());
            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException e) {
            var erroDTo = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDTo.status()).body(erroDTo);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar Autores", description = "Pesquisar autores")
    @ApiResponses({
            @ApiResponse(responseCode =  "200", description = "Sucesso")
    })
    public ResponseEntity<List<AutorDTO>> listarAutorDto(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        //testes especificos
        log.trace("Pesquia Autores");
        log.debug("Pesquia Autores");
        log.info("Pesquia Autores");
        log.warn("Pesquia Autores");
        log.error("Pesquia Autores");

        List<Autor> lista = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> list = lista.stream().map(mapper::toDTO).collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Atualizar Autores", description = "Atualizar autores pelo UUID")
    @ApiResponses({
            @ApiResponse(responseCode =  "204", description = "Autor atualizado com sucesso"),
            @ApiResponse(responseCode =  "404", description = "Autor não encotrado"),
            @ApiResponse(responseCode =  "409", description = "Autor já cadastrado")
    })
    public ResponseEntity<Object> atualizarAutor(@Valid @PathVariable("id") String id, @Valid @RequestBody AutorDTO dto) {
        log.info("Atualizando autor: {}", dto.nome());
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autor = autorService.obterId(idAutor);
            if (autor.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            var autorFinal = autor.get();
            autorFinal.setNome(dto.nome());
            autorFinal.setNascionalidade(dto.nascionalidade());
            autorFinal.setDataNascimento(dto.dataNascimento());

            autorService.atualizar(autorFinal);

            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e) {
            var erroDTo = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDTo.status()).body(erroDTo);
        }

    }
}

