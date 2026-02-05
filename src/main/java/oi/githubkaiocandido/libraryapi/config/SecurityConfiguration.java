package oi.githubkaiocandido.libraryapi.config;

import oi.githubkaiocandido.libraryapi.Service.UsuariosService;
import oi.githubkaiocandido.libraryapi.security.LoginSocialSucessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import oi.githubkaiocandido.libraryapi.security.CustomUserDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, LoginSocialSucessHandler loginSocialSucessHandler) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(configurer -> configurer.loginPage("/login").permitAll())
                .authorizeHttpRequests(authorize ->{
                            authorize.requestMatchers("/login").permitAll();
                            authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
                            authorize.anyRequest().authenticated();

                }).oauth2Login(Customizer.withDefaults())
                .oauth2Login(ouath2 -> {
                    ouath2.loginPage("/login")
                    .successHandler(loginSocialSucessHandler);
                })
                .oauth2ResourceServer(oauthTwoRs -> oauthTwoRs.jwt(Customizer.withDefaults()))
                .build();
    }

    //CONFIGURA NO ROLA JWT O PREFIX
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }


    //CONFIGURA NO TOKEN JWT O PREFIX SCOPE
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        var authoritesConvert = new JwtGrantedAuthoritiesConverter();
        authoritesConvert.setAuthorityPrefix("");

        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritesConvert);

        return converter;

    }
}
