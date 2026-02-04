package oi.githubkaiocandido.libraryapi.repository;

import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Usuario findByLogin(String login);

    Usuario findByEmail(String email);
}
