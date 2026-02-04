package oi.githubkaiocandido.libraryapi.controller;

import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Service.ClientService;
import oi.githubkaiocandido.libraryapi.model.Client;
import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salvar(@RequestBody Client client){
        clientService.salvar(client);
    }
}
