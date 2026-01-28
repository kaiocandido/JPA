package oi.githubkaiocandido.libraryapi.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Service.UsuariosService;
import oi.githubkaiocandido.libraryapi.controller.dto.UsuarioDto;
import oi.githubkaiocandido.libraryapi.controller.mappers.UsuarioMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuariosService serive;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid UsuarioDto dto){
        var usuario = mapper.toEntity(dto);
        serive.salvar(usuario);
    }
}
