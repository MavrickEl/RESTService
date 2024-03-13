package com.example.restAPI.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookRequest {
    private Long id;
    private String title;
    private Long authorId;
}
