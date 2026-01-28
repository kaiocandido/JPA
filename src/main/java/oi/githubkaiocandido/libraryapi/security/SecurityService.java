package oi.githubkaiocandido.libraryapi.security;

import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Service.UsuariosSerive;
import oi.githubkaiocandido.libraryapi.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuariosSerive usuariosSerive;

    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        return usuariosSerive.obterPorLogin(login);
    }
}
