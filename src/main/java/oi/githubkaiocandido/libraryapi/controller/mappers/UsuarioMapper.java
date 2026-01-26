package oi.githubkaiocandido.libraryapi.controller.mappers;

import oi.githubkaiocandido.libraryapi.controller.dto.UsuarioDto;
import oi.githubkaiocandido.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDto usuarioDto);
}
