package oi.githubkaiocandido.libraryapi.controller.mappers;

import oi.githubkaiocandido.libraryapi.controller.dto.AutorDTO;
import oi.githubkaiocandido.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {
    Autor toEntity(AutorDTO autorDTO);

    AutorDTO toDTO(Autor autor);
}
