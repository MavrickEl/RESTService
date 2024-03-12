package repo;

import entity.Author;

import java.util.List;

public interface AuthorRepository {
    Author save(Author author);
    Author findById(Long id);
    List<Author> findAll();
    void update(Long id, Author author);
    void delete(Long id);
}
