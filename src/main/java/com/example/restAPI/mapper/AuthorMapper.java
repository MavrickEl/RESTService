package com.example.restAPI.mapper;

import com.example.restAPI.dto.AuthorRequest;
import com.example.restAPI.dto.AuthorResponse;
import com.example.restAPI.entity.Author;

public interface AuthorMapper {
    Author toEntity(AuthorRequest authorRequest);

    AuthorResponse toDtoResponse(Author author);
}
