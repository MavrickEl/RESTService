package com.example.restAPI.service.Impl;

import com.example.restAPI.entity.BookAuthor;
import com.example.restAPI.repo.BookAuthorRepository;
import com.example.restAPI.repo.Impl.BookAuthorRepoImpl;
import com.example.restAPI.service.BookAuthorService;

import java.sql.SQLException;

public class BookAuthorServiceImpl implements BookAuthorService {
    BookAuthorRepository bookAuthorRepository = new BookAuthorRepoImpl();

    @Override
    public void save(BookAuthor bookAuthor) throws SQLException {
        bookAuthorRepository.save(bookAuthor);
    }

}
