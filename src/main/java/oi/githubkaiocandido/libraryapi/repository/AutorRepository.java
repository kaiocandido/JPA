package oi.githubkaiocandido.libraryapi.repository;

import oi.githubkaiocandido.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

}
