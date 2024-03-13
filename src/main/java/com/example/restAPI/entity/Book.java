package com.example.restAPI.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Book {
    private Long id;
    private String title;
    private Long authorId;
    private List<Author> authors;
}
