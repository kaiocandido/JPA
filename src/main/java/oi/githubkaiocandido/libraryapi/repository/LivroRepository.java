package oi.githubkaiocandido.libraryapi.repository;

import oi.githubkaiocandido.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
}
