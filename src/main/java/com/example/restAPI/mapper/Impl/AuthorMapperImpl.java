package com.example.restAPI.mapper.Impl;

import com.example.restAPI.dto.AuthorRequest;
import com.example.restAPI.dto.AuthorResponse;
import com.example.restAPI.entity.Author;
import com.example.restAPI.mapper.AuthorMapper;

public class AuthorMapperImpl implements AuthorMapper {
    @Override
    public Author toEntity(AuthorRequest authorRequest) {
        return Author.builder()
                .id(authorRequest.getId())
                .name(authorRequest.getName())
                .secondName(authorRequest.getSecondName())
                .build();
    }

    @Override
    public AuthorResponse toDtoResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .secondName(author.getSecondName())
                .books(author.getBooks())
                .build();
    }
}
