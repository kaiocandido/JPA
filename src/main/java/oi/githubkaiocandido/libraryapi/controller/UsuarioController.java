package oi.githubkaiocandido.libraryapi.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Tag(name = "Usuarios")
@Slf4j
public class UsuarioController {

    private final UsuariosService serive;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Usuario", description = "Salvar usuario")
    @ApiResponses({
            @ApiResponse(responseCode =  "204", description = "Usuario cadastrado com sucesso")
    })
    public void salvar(@RequestBody @Valid UsuarioDto dto){
        log.info("Usuario cadastrado {}", dto.login());
        var usuario = mapper.toEntity(dto);
        serive.salvar(usuario);
    }
}
