package com.example.restAPI.service.Impl;

import com.example.restAPI.dto.AuthorRequest;
import com.example.restAPI.entity.Author;
import com.example.restAPI.entity.BookAuthor;
import com.example.restAPI.repo.BookAuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookAuthorServiceImplTest {

    @Mock
    BookAuthorRepository bookAuthorRepository;
    @InjectMocks
    BookAuthorServiceImpl bookAuthorService;
    BookAuthor bookAuthor;

    @BeforeEach
    void setUp() {
        bookAuthor = BookAuthor.builder().id(1L).bookId(1L).authorId(1L).build();
    }

    @Test
    void testSave() throws SQLException {

        doNothing().when(bookAuthorRepository).save(bookAuthor);

        bookAuthorService.save(bookAuthor);

        verify(bookAuthorRepository,times(1)).save(bookAuthor);
    }

    @Test
    void testSaveWithInvalidData() throws SQLException {
        doThrow(SQLException.class).when(bookAuthorRepository).save(bookAuthor);

        assertThrows(SQLException.class, () -> bookAuthorService.save(bookAuthor));
    }
}
