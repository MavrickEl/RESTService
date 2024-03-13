package com.example.restAPI.mapper;

import com.example.restAPI.dto.AuthorRequest;
import com.example.restAPI.dto.AuthorResponse;
import com.example.restAPI.entity.Author;
import com.example.restAPI.entity.Book;

import java.util.List;

public interface AuthorMapper {
    Author toEntity(AuthorRequest authorRequest);

    AuthorResponse toDtoResponse(Author author);
    AuthorResponse toDtoResponseWithBooks(Author author, List<Book> books);
}
