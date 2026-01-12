package oi.githubkaiocandido.libraryapi.repository;

import oi.githubkaiocandido.libraryapi.model.Autor;
import oi.githubkaiocandido.libraryapi.model.Generos;
import oi.githubkaiocandido.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("6444446354");
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setGenero(Generos.CIENCIA);
        livro.setTitulo("Doidos");
        livro.setDataPublicacao(LocalDate.of(1990, 1 , 10));

        Autor autor = autorRepository.findById(UUID.fromString("d67e5f2c-28f2-4636-834b-b2d4a331649f"))
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    public void salvarCascadeSalvaTest(){
        Livro livro = new Livro();
        livro.setIsbn("6444446354");
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setGenero(Generos.CIENCIA);
        livro.setTitulo("outro livro");
        livro.setDataPublicacao(LocalDate.of(1990, 1 , 10));

        Autor autor = new Autor();
        autor.setNome("Joana");
        autor.setNascionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2002, 1, 27));

        autorRepository.save(autor);

        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    @Test
    public void atualizarAutorLivro(){
        UUID livroId = UUID.fromString("edea5550-b839-4a17-9646-3568b87f8078");
        UUID autorId = UUID.fromString("edea5550-b839-4a17-9646-3568b87f8078");

        Livro livroParaAtt = livroRepository.findById(livroId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        livroParaAtt.setAutor(autor);
        livroRepository.save(livroParaAtt);
    }

    @Test
    public void buscarLivroTest(){
        UUID id = UUID.fromString("edea5550-b839-4a17-9646-3568b87f8078");
        Livro livro = livroRepository.findById(id).orElse(null);

        if (livro == null) return;

        System.out.println("Livro:");
        System.out.println(livro.getTitulo());
        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    public void pesquisaPorTituloTest(){
        List<Livro> byTitulo = livroRepository.findByTitulo("Doidos");
        byTitulo.forEach(System.out::println);
    }

    @Test
    public void pesquisarPorIsbnTest(){
        List<Livro> byIsbn = livroRepository.findByIsbn("6444446354");
        byIsbn.forEach(System.out::println);
    }

    @Test
    public void pesquisaPorTituloAndPrecoTest(){
        List<Livro> byTituloAndPreco = livroRepository.findByTituloAndPreco("Doidos", BigDecimal.valueOf(200));
        byTituloAndPreco.forEach(System.out::println);
    }

    @Test
    public void pesquisarPorTituloOrIsbn(){
        List<Livro> byTituloOrIsbn = livroRepository.findByTituloOrIsbn("Doidos", "6444446354");
        byTituloOrIsbn.forEach(System.out::println);
    }

    @Test
    public void pesquisarPorDataInicioAndFim(){
        List<Livro> byInicioAndFimData = livroRepository.findByDataPublicacaoBetween(
                LocalDate.of(1990, 1 , 10),
                LocalDate.of(2004, 1 , 10)
        );
        byInicioAndFimData.forEach(System.out::println);
    }

    @Test
    public void pesquisarPorPalavra(){
        List<Livro> listPalavras = livroRepository.findByTituloContainingIgnoreCase("carro");
        listPalavras.forEach(System.out::println);
    }

    @Test
    public void pesquisarPorTituloOuIsbnAndOrdenarPorTitulo(){
        List<Livro> livrosOrdenados = livroRepository.findByTituloOrIsbnOrderByTituloAsc("Doidos", "6444446354");
        livrosOrdenados.forEach(System.out::println);
    }

    // JPQL
    @Test
    public void listarLivros(){
        var livrosResultado = livroRepository.listarTodosOrdenadoPorTituloAndPreco();
        livrosResultado.forEach(System.out::println);
    }

    @Test
    public void listarAutorLivros(){
        var autores = livroRepository.listarAutoresDosLivros();
        autores.forEach(System.out::println);
    }


    @Test
    public void listarGenerosBrasileiros(){
        var generos = livroRepository.listarGenerosAutorBrasileiros();
        generos.forEach(System.out::println);
    }
}
