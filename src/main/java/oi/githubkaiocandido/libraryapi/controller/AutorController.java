package oi.githubkaiocandido.libraryapi.controller;

import oi.githubkaiocandido.libraryapi.Service.AutorService;
import oi.githubkaiocandido.libraryapi.controller.dto.AutorDTO;
import oi.githubkaiocandido.libraryapi.model.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores") // -> host http:localhost:8080/autores

public class AutorController {

    @Autowired
    private AutorService autorService;

    public AutorController(AutorService autorService){
        this.autorService = autorService;
    }

    @PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor){
        var autorEntidade = autor.mapearParaAutor();
        autorService.salvar(autorEntidade);

        // -> host http:localhost:8080/autores/id
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autorEntidade.getId()).toUri();

        return  ResponseEntity.created(location).build();
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
    public ResponseEntity<Void> deletar(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autor = autorService.obterId(idAutor);
        if (autor.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        autorService.deletar(autor.get());
        return ResponseEntity.noContent().build();
    }

}
