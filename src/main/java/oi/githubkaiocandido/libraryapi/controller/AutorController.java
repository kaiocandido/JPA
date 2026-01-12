package oi.githubkaiocandido.libraryapi.controller;

import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Exceptions.OperacaoNaoPermitidaException;
import oi.githubkaiocandido.libraryapi.Exceptions.RegistroDuplicadoException;
import oi.githubkaiocandido.libraryapi.Service.AutorService;
import oi.githubkaiocandido.libraryapi.controller.dto.AutorDTO;
import oi.githubkaiocandido.libraryapi.controller.dto.ErroResposta;
import oi.githubkaiocandido.libraryapi.model.Autor;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores") // -> host http:localhost:8080/autores
@RequiredArgsConstructor
public class AutorController {


    private final AutorService autorService;

    @PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> salvar(@RequestBody AutorDTO autor){
        try {
            var autorEntidade = autor.mapearParaAutor();
            autorService.salvar(autorEntidade);

            // -> host http:localhost:8080/autores/id
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autorEntidade.getId()).toUri();

            return  ResponseEntity.created(location).build();
        }catch (RegistroDuplicadoException e ){
            var erroDTo = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTo.status()).body(erroDTo);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);

        Optional<Autor> autor = autorService.obterId(idAutor);
        if (autor.isPresent()){
            Autor entidade = autor.get();
            AutorDTO dto = new AutorDTO(entidade.getId(), entidade.getNome(), entidade.getDataNascimento(), entidade.getNascionalidade());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autor = autorService.obterId(idAutor);

            if (autor.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            autorService.deletar(autor.get());
            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException e ){
            var erroDTo = ErroResposta.respostaPadrão(e.getMessage());
            return ResponseEntity.status(erroDTo.status()).body(erroDTo);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> listarAutorDto(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value ="nacionalidade", required = false) String nacionalidade){

        List<Autor> lista = autorService.pesquisar(nome, nacionalidade);
        List<AutorDTO> list = lista.stream().map(autor -> new AutorDTO(
                autor.getId(),
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNascionalidade()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizarAutor(@PathVariable("id") String id, @RequestBody AutorDTO dto){

        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autor = autorService.obterId(idAutor);
            if (autor.isEmpty()){
                return ResponseEntity.noContent().build();
            }

            var autorFinal = autor.get();
            autorFinal.setNome(dto.nome());
            autorFinal.setNascionalidade(dto.nascionalidade());
            autorFinal.setDataNascimento(dto.dataNascimento());

            autorService.atualizar(autorFinal);

            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e ){
            var erroDTo = ErroResposta.respostaPadrão(e.getMessage());
            return ResponseEntity.status(erroDTo.status()).body(erroDTo);
        }

    }
}

