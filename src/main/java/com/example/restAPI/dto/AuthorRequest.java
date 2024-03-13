package com.example.restAPI.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorRequest {
    private Long id;
    private String name;
    private String secondName;
}
