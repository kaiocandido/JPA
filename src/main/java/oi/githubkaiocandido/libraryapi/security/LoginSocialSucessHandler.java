package oi.githubkaiocandido.libraryapi.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.Service.UsuariosService;
import oi.githubkaiocandido.libraryapi.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final PasswordEncoder encoder;
    private static final String  SENHA_PADRAO = "123";
    private final UsuariosService service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();
        String email = oAuth2User.getAttribute("email");


        super.onAuthenticationSuccess(request, response, authentication);

        Usuario usuario = service.obterPorEmail(email);

        if (usuario == null){
            usuario = cadastrarUsuarioNaBase(email);
        }

        CustomAuthentication customAuthentication = new CustomAuthentication(usuario);

        SecurityContextHolder.getContext().setAuthentication(customAuthentication);

        super.onAuthenticationSuccess(request, response, authentication);


    }

    private Usuario cadastrarUsuarioNaBase(String email) {
        Usuario usuario;
        usuario = new Usuario();
        usuario.setLogin(tratarEmail(email));
        usuario.setPassword(SENHA_PADRAO);
        usuario.setEmail(email);
        usuario.setRoles(List.of(("OPERADOR")));
        service.salvar(usuario);
        return usuario;
    }

    private String tratarEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
