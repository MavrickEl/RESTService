package service;

import dto.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO save(BookDTO bookDTO);
    BookDTO findById(Long id);
    List<BookDTO> findAll();
    void delete(Long id);
}
