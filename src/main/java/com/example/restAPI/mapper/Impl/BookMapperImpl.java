package com.example.restAPI.mapper.Impl;

import com.example.restAPI.dto.BookRequest;
import com.example.restAPI.dto.BookResponse;
import com.example.restAPI.entity.Book;
import com.example.restAPI.mapper.BookMapper;

public class BookMapperImpl implements BookMapper {
    @Override
    public Book toEntity(BookRequest bookRequest) {
        return Book.builder()
                .id(bookRequest.getId())
                .title(bookRequest.getTitle())
                .description(bookRequest.getDescription())
                .build();
    }


    @Override
    public BookResponse toDtoResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .authors(book.getAuthors())
                .build();
    }
}
