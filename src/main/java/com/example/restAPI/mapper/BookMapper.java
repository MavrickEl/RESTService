package com.example.restAPI.mapper;

import com.example.restAPI.dto.AuthorResponse;
import com.example.restAPI.dto.BookRequest;
import com.example.restAPI.dto.BookResponse;
import com.example.restAPI.entity.Author;
import com.example.restAPI.entity.Book;

import java.util.List;

public interface BookMapper {
    Book toEntity(BookRequest bookDto);

    BookResponse toDtoResponse(Book book);

    BookResponse toDtoResponseWithAuthors(Book book, List<Author> authors);

}
