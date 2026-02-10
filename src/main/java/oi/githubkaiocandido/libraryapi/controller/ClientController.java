package oi.githubkaiocandido.libraryapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Service.ClientService;
import oi.githubkaiocandido.libraryapi.model.Client;
import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Tag(name = "Cliente")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar Clientes", description = "Salvar Clientes de Autenticação")
    @ApiResponses({
            @ApiResponse(responseCode =  "201", description = "Cliente cadastrado com sucesso"),
            @ApiResponse(responseCode =  "422", description = "Erro de validação"),
            @ApiResponse(responseCode =  "409", description = "Cliente já cadastrado")
    })
    public void salvar(@RequestBody Client client){
        clientService.salvar(client);
    }
}
