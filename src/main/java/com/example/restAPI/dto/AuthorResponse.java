package com.example.restAPI.dto;

import com.example.restAPI.entity.Book;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuthorResponse {
    private Long id;
    private String name;
    private String secondName;
    //TODO поменять на BookResponse
    private List<Book> books;
}
