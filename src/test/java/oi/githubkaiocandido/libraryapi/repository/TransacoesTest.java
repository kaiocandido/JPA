package oi.githubkaiocandido.libraryapi.repository;

import oi.githubkaiocandido.libraryapi.Service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {
    @Autowired
    AutorRepository autorRepository;

    @Autowired
    TransacaoService transacaoService;

    /**
     * Commit - Confirmar alterações
     * Rollback - Desfazer alterações
    **/

    @Test
    public void transacoesSimples(){
        // salvar um livro
        // salvar o autor
        // alugar o livro
        // enviar o email pro cliente
        // notificar a entrega

        transacaoService.executar();

    }
}
