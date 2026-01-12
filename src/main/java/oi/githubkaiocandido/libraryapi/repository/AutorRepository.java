package oi.githubkaiocandido.libraryapi.repository;

import oi.githubkaiocandido.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** @see AutorRepositoryTest
 */


public interface AutorRepository extends JpaRepository<Autor, UUID> {
    List<Autor> findByNomeContainingIgnoreCase(String nome);

    List<Autor> findByNascionalidadeContainingIgnoreCase(String nascionalidade);

    List<Autor> findByNomeContainingIgnoreCaseAndNascionalidadeContainingIgnoreCase(
            String nome,
            String nascionalidade
    );

    Optional<Autor> findByNomeAndDataNascimentoAndNascionalidade(String nome, LocalDate dataNascimento, String nascionalidade);

}
