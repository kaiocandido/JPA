package oi.githubkaiocandido.libraryapi.security;

import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Service.UsuariosSerive;
import oi.githubkaiocandido.libraryapi.model.Usuario;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAutheticationProvaider implements AuthenticationProvider {

    private final UsuariosSerive usuariosSerive;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Usuario usuario = usuariosSerive.obterPorLogin(login);

        if (usuario == null){
            throw new UsernameNotFoundException("Usuario/ou Senha incorreta");
        }

        String senhaCriptrogragada = usuario.getPassword();

        boolean senhasBatem = passwordEncoder.matches(senhaDigitada, senhaCriptrogragada);

        if (senhasBatem ){
            return new CustomAuthentication(usuario);
        }

        throw new UsernameNotFoundException("Usuario/ou Senha incorreta");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
