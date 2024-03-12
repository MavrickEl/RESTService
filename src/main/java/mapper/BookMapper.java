package mapper;

import dto.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.awt.print.Book;
import java.util.List;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toEntity(BookDTO bookDto);

    BookDTO toDto(Book book);

    List<BookDTO> toListDto(List<Book> books);

}
