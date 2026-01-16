package oi.githubkaiocandido.libraryapi.repository.specs;

import oi.githubkaiocandido.libraryapi.model.Generos;
import oi.githubkaiocandido.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike (String titulo){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(Generos genero){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoEqual(Integer anoPublicacao){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.
                                function("to_char",
                                        String.class,
                                        root.get("dataPublicacao"), criteriaBuilder.literal("YYYY")),anoPublicacao.toString());
    }


    public static Specification<Livro> nomeAutorLike(String nome){
        return (root, query, criteriaBuilder) -> {

            //sempre usar esse
            return criteriaBuilder.like(criteriaBuilder.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%");

            /*usando Join

            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
            return criteriaBuilder.like(criteriaBuilder.upper(joinAutor.get("nome")), "%" + nome + "%");

             */

        };
    }



}
