package oi.githubkaiocandido.libraryapi;

import oi.githubkaiocandido.libraryapi.model.Autor;
import oi.githubkaiocandido.libraryapi.model.Livro;
import oi.githubkaiocandido.libraryapi.repository.AutorRepository;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.LocalDate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
		builder.bannerMode(Banner.Mode.OFF);
		builder.run(args);

		var context = SpringApplication.run(Application.class, args);
		AutorRepository repository = context.getBean(AutorRepository.class);

		exemploSalvar(repository);
	}

	public static void exemploSalvar(AutorRepository autorRepository){
		Autor autor = new Autor();
		autor.setNome("Kaio");
		autor.setNascionalidade("Brasileira");
		autor.setDataNascimento(LocalDate.of(2001, 01, 27));

		var autorSalvo = autorRepository.save(autor);

		System.out.println("Autor Salvo: " + autorSalvo.getId());
	}

}
