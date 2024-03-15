package com.example.restAPI.service;

import com.example.restAPI.dto.AuthorRequest;
import com.example.restAPI.dto.AuthorResponse;

import java.sql.SQLException;
import java.util.List;

public interface AuthorService {
    void save(AuthorRequest authorRequest) throws SQLException;
    AuthorResponse getById(Long id) throws SQLException;
    List<AuthorResponse> getAll() throws SQLException;

    void update(AuthorRequest authorRequest) throws SQLException;

    void delete(Long id) throws SQLException;
}
