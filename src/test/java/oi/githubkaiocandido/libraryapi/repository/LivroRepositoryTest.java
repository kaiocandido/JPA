package oi.githubkaiocandido.libraryapi.repository;

import oi.githubkaiocandido.libraryapi.model.Autor;
import oi.githubkaiocandido.libraryapi.model.Generos;
import oi.githubkaiocandido.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
        livro.setData_publicacao(LocalDate.of(1990, 1 , 10));

        Autor autor = autorRepository.findById(UUID.fromString("edea5550-b839-4a17-9646-3568b87f8078")).orElse(null);
        livro.setId_autor(autor);

        livroRepository.save(livro);
    }

    @Test
    public void salvarCascadeSalvaTest(){
        Livro livro = new Livro();
        livro.setIsbn("6444446354");
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setGenero(Generos.CIENCIA);
        livro.setTitulo("outro livro");
        livro.setData_publicacao(LocalDate.of(1990, 1 , 10));

        Autor autor = new Autor();

        autor.setNome("Joana");
        autor.setNascionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2002, 01, 27));

        autorRepository.save(autor);
        livro.setId_autor(autor);
        livroRepository.save(livro);
    }

    @Test
    public void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("6444446354");
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setGenero(Generos.CIENCIA);
        livro.setTitulo("outro livro");
        livro.setData_publicacao(LocalDate.of(1990, 1 , 10));

        Autor autor = new Autor();

        autor.setNome("Joana");
        autor.setNascionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2002, 01, 27));

        livro.setId_autor(autor);
        livroRepository.save(livro);
    }

    @Test
    public void atualizarAutorLivro(){
         var livroParaAtt = livroRepository.findById(UUID.fromString("edea5550-b839-4a17-9646-3568b87f8078")).orElse(null);

         Autor maria = autorRepository.findById(UUID.fromString("edea5550-b839-4a17-9646-3568b87f8078")).orElse(null);

        livroParaAtt.setId_autor(maria);
        livroRepository.save(livroParaAtt);

    }

    @Test
    public void deletar(){
        UUID id = (UUID.fromString("edea5550-b839-4a17-9646-3568b87f8078"));

        livroRepository.deleteById(id);
    }

    @Test
    public void deletarCascade(){
        UUID id = (UUID.fromString("edea5550-b839-4a17-9646-3568b87f8078"));

        livroRepository.deleteById(id);
    }

    @Test
    //@Transactional
    public void buscarLivroTest(){
        UUID  id =  (UUID.fromString("edea5550-b839-4a17-9646-3568b87f8078"));
        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println("Livro:");
        System.out.println(livro.getTitulo());
        System.out.println("Autor:");
        System.out.println(livro.getId_autor().getNome());

    }


    @Test
    public void pesquisaPorTituloTest(){
        List<Livro> byTitulo = livroRepository.findByTitulo("");
        byTitulo.forEach(System.out::println);
    }

    @Test
    public void pesquisarPorIsnbTest(){
        List<Livro> byIsbn = livroRepository.findByIsbn("");
        byIsbn.forEach(System.out::println);
    }

    @Test
    public void pesquisaPorTituloAndPrecoTest(){
        List<Livro> byTituloAndPreco = livroRepository.findByTituloAndPreco("", BigDecimal.valueOf(158.10));
        byTituloAndPreco.forEach(System.out::println);
    }

    @Test
    public void pesquisarPorTituloOrIsnb(){
        List<Livro> byTituloOrIsbn = livroRepository.findByTituloOrIsbn("", "");
        byTituloOrIsbn.forEach(System.out::println);
    }

    @Test
    public void pesquisarPorDataInicioAndFim(){
        List<Livro> byInicioAndFimData = livroRepository.findByDataPublicacaoBetween(LocalDate.of(1990, 1 , 10), LocalDate.of(2004, 1 , 10));
        byInicioAndFimData.forEach(System.out::println);
    }

    @Test
    public void pesquisarPorPalavra(){
        List<Livro> listPalavras = livroRepository.findByDataTituloLike("Carro");
        listPalavras.forEach(System.out::println);
    }

    @Test
    public void pesquisarPorTituloOuIsbnAndOrdenarPorTitulo(){
        List<Livro> livrosOrdenados = livroRepository.findByTituluOrIsbnOrdeByTitulo("", "");
        livrosOrdenados.forEach(System.out::println);
    }

    @Test
    public void pesquisarPorTituloQualquer(){
        List<Livro> livrosIgnoreCase = livroRepository.findByTituloIgnoreCase("");
        livrosIgnoreCase.forEach(System.out::println);
    }

    //Usando JPQL
    @Test
    public void ListarLivros(){
        var livrosResultado = livroRepository.listarTodosOrdenadoPorTituloAndPreco();
        livrosResultado.forEach(System.out::println);
    }

    @Test
    public void ListarAutorLivros(){
        var livrosResultado = livroRepository.listarAutoresDosLivros();
        livrosResultado.forEach(System.out::println);
    }

    @Test
    public void ListarTitulosLivros(){
        var livrosResultado = livroRepository.listarNomesLivros();
        livrosResultado.forEach(System.out::println);
    }

    @Test
    public void ListarGenerosBrasileiros(){
        var livrosResultado = livroRepository.listarGenerosAutorBrasileiros();
        livrosResultado.forEach(System.out::println);
    }

    @Test
    public void ListarOGeneroQueryParam(){
        var resultado = livroRepository.findByGenero(Generos.CIENCIA, "dataPublicacao");
    }

    @Test
    public void ListarOGeneroQueryParamPositional(){
        var resultado = livroRepository.findByGeneroPositional(Generos.CIENCIA, "dataPublicacao");
    }

}

