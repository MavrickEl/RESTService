package com.example.restAPI.service;

import com.example.restAPI.dto.BookRequest;
import com.example.restAPI.dto.BookResponse;

import java.sql.SQLException;
import java.util.List;

public interface BookService {
    BookResponse save(BookRequest bookRequest) throws SQLException;

    BookResponse getById(Long id) throws SQLException;

    List<BookResponse> getAll() throws SQLException;

    BookResponse update(Long id, BookRequest bookRequest) throws SQLException;

    void delete(Long id) throws SQLException;
}
