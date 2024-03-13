package com.example.restAPI.dto;

import com.example.restAPI.entity.Author;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private Long authorId;
    //TODO AuthorResponse
    private List<Author> authors;
}
