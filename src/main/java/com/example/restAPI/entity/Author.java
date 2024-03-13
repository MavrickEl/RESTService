package com.example.restAPI.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Author {
    private Long id;
    private String name;
    private String secondName;
    private List<Book> books;
}
