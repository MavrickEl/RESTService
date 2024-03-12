package repo;

import entity.Book;

import java.util.List;

public interface BookRepository {
    Book save(Book book);

    Book findById(Long id);

    List<Book> findAll();

    void update(Long id, Book author);

    void delete(Long id);
}
