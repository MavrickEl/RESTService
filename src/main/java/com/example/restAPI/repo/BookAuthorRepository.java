package com.example.restAPI.repo;

import com.example.restAPI.entity.BookAuthor;

import java.sql.SQLException;

public interface BookAuthorRepository {
    void save(BookAuthor bookAuthor) throws SQLException;

}

