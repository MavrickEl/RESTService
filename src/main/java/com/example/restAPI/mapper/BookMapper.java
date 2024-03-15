package com.example.restAPI.mapper;

import com.example.restAPI.dto.BookRequest;
import com.example.restAPI.dto.BookResponse;
import com.example.restAPI.entity.Book;

public interface BookMapper {
    Book toEntity(BookRequest bookDto);

    BookResponse toDtoResponse(Book book);

}
