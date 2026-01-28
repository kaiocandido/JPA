package oi.githubkaiocandido.libraryapi.security;

import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Service.UsuariosSerive;
import oi.githubkaiocandido.libraryapi.model.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private  final UsuariosSerive service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = service.obterPorLogin(login);

        if (login == null){
            throw  new UsernameNotFoundException("Usuario não encontrado");
        }

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getPassword()).roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
                .build();

    }


}
