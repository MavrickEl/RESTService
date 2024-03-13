package com.example.restAPI.repo;

import com.example.restAPI.entity.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookRepository {
    Book save(Book book) throws SQLException;

    Book findById(Long id) throws SQLException;

    List<Book> findAll() throws SQLException;

    Book update(Long id, Book book) throws SQLException;

    void delete(Long id) throws SQLException;

}
