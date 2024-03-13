package com.example.restAPI.service.Impl;

import com.example.restAPI.dto.AuthorRequest;
import com.example.restAPI.dto.AuthorResponse;
import com.example.restAPI.entity.Author;
import com.example.restAPI.mapper.AuthorMapper;
import com.example.restAPI.mapper.Impl.AuthorMapperImpl;
import com.example.restAPI.repo.AuthorRepository;
import com.example.restAPI.repo.Impl.AuthorRepoImpl;
import com.example.restAPI.service.AuthorService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepo = new AuthorRepoImpl();
    private final AuthorMapper mapper = new AuthorMapperImpl();

    @Override
    public AuthorResponse save(AuthorRequest authorRequest) throws SQLException {
        return mapper.toDtoResponse(authorRepo.save(mapper.toEntity(authorRequest)));
    }

    @Override
    public AuthorResponse getById(Long id) throws SQLException {
        Author author = authorRepo.findById(id);
        return (author != null) ? mapper.toDtoResponseWithBooks(author, author.getBooks()) : null;
    }

    @Override
    public List<AuthorResponse> getAll() throws SQLException {
        List<Author> authors = authorRepo.findAll();
        List<AuthorResponse> authorResponses = new ArrayList<>();
        for (Author author : authors) {
            authorResponses.add(mapper.toDtoResponseWithBooks(author, author.getBooks()));
        }
        return authorResponses;
    }
    @Override
    public AuthorResponse update(Long id, AuthorRequest authorRequest) throws SQLException {
        Author author = authorRepo.update(id, mapper.toEntity(authorRequest));
        return mapper.toDtoResponse(author);
    }

    @Override
    public void delete(Long id) throws SQLException {
        authorRepo.delete(id);
    }

}
