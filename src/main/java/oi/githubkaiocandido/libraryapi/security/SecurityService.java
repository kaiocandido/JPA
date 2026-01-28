package oi.githubkaiocandido.libraryapi.security;

import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Service.UsuariosService;
import oi.githubkaiocandido.libraryapi.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuariosService usuariosSerive;

    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof CustomAuthentication customAuthentication){
            return customAuthentication.getUsuario();
        }

        return null;
    }
}
