package oi.githubkaiocandido.libraryapi.Service;

import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.model.Usuario;
import oi.githubkaiocandido.libraryapi.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuariosService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void salvar(Usuario usuario){
        var senha = usuario.getPassword();
        usuario.setPassword(passwordEncoder.encode(senha));
        repository.save(usuario);
    }

    public Usuario obterPorLogin(String login){
        return repository.findByLogin(login);
    }

    public Usuario obterPorEmail (String email){
        return repository.findByEmail();
    }

}
