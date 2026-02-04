package oi.githubkaiocandido.libraryapi.Service;

import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.controller.ClientController;
import oi.githubkaiocandido.libraryapi.model.Client;
import oi.githubkaiocandido.libraryapi.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private  final ClientRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Client salvar(Client client){
        var senhaCriptrografada = passwordEncoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptrografada);
        return repository.save(client);
    }

    public Client obterPorClientId(String clientId){
                return repository.findByClientId(clientId);
    }
}
