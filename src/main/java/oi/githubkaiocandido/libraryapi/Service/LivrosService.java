package oi.githubkaiocandido.libraryapi.Service;

import lombok.RequiredArgsConstructor;
import oi.githubkaiocandido.libraryapi.repository.LivroRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivrosService {

    private final LivroRepository  livroRepository;
}
