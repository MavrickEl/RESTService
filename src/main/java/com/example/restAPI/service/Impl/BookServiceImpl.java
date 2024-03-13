package com.example.restAPI.service.Impl;

import com.example.restAPI.dto.BookRequest;
import com.example.restAPI.dto.BookResponse;
import com.example.restAPI.entity.Book;
import com.example.restAPI.mapper.BookMapper;
import com.example.restAPI.mapper.Impl.BookMapperImpl;
import com.example.restAPI.repo.BookRepository;
import com.example.restAPI.repo.Impl.BookRepoImpl;
import com.example.restAPI.service.BookService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepo = new BookRepoImpl();
    private final BookMapper mapper = new BookMapperImpl();

    @Override
    public BookResponse save(BookRequest bookRequest) throws SQLException {
        Book book = bookRepo.save(mapper.toEntity(bookRequest));
        return mapper.toDtoResponseWithAuthors(book, book.getAuthors());
    }

    @Override
    public BookResponse getById(Long id) throws SQLException {
        Book book = bookRepo.findById(id);
        return mapper.toDtoResponseWithAuthors(book, book.getAuthors());
    }

    @Override
    public List<BookResponse> getAll() throws SQLException {
        List<Book> books = bookRepo.findAll();
        return books.stream()
                .map((Book book) -> mapper.toDtoResponseWithAuthors(book, book.getAuthors()))
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse update(Long id, BookRequest bookRequest) throws SQLException {
        Book book = bookRepo.update(id, mapper.toEntity(bookRequest));
        return mapper.toDtoResponse(book);
    }

    @Override
    public void delete(Long id) throws SQLException {
        bookRepo.delete(id);

    }
}
