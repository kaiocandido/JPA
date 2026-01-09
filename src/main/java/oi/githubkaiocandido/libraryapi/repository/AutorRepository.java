package oi.githubkaiocandido.libraryapi.repository;

import oi.githubkaiocandido.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** @see AutorRepositoryTest
 */


public interface AutorRepository extends JpaRepository<Autor, UUID> {
    List<Autor> findByNomeLike(String nome);
    List<Autor> findByNascionalidadeLike(String nascionalidade);
    List<Autor> findByNomeAndNascionalidadeLike(String nome, String nascionalidade);
}
