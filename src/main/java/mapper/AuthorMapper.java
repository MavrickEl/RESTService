package mapper;

import dto.AuthorDTO;
import entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toEntity(AuthorDTO authorDTO);

    AuthorDTO toDto(Author author);

    List<AuthorDTO> toListDto(List<Author> authors);


}
