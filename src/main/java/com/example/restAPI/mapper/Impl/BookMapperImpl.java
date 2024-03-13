package com.example.restAPI.mapper.Impl;

import com.example.restAPI.dto.BookRequest;
import com.example.restAPI.dto.BookResponse;
import com.example.restAPI.entity.Author;
import com.example.restAPI.entity.Book;
import com.example.restAPI.mapper.BookMapper;

import java.util.List;

public class BookMapperImpl implements BookMapper {
    @Override
    public Book toEntity(BookRequest bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .authorId(bookDto.getAuthorId())
                .build();
    }


    @Override
    public BookResponse toDtoResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .build();
    }

    @Override
    public BookResponse toDtoResponseWithAuthors(Book book, List<Author> authors) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authors(authors)
                .build();
    }
}
