package com.example.restAPI.service;

import com.example.restAPI.entity.BookAuthor;

import java.sql.SQLException;
import java.util.List;

public interface BookAuthorService {
    void save(BookAuthor bookAuthor) throws SQLException;

}

