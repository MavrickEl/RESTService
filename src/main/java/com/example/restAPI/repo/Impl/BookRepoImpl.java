package com.example.restAPI.repo.Impl;

import com.example.restAPI.entity.Author;
import com.example.restAPI.entity.Book;
import com.example.restAPI.repo.BookRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.restAPI.utils.DBUtil.*;

public class BookRepoImpl implements BookRepository {
    @Override
    public Book save(Book book) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        String sql = "INSERT INTO book (title, author_id) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, book.getTitle());
        statement.setLong(2, book.getAuthorId());

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Не удалось создать книгу");
        }

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            book.setId(generatedKeys.getLong(1));
        } else {
            throw new SQLException("Не удалось создать книгу, идентификатор не получен");
        }

        return book;
    }

    @Override
    public Book findById(Long id) throws SQLException {
        Book book;
        String sql = "SELECT b.*, a.id AS author_id, a.name, a.second_name  " +
                "FROM book b " +
                "JOIN author a ON b.author_id = a.id " +
                "WHERE b.id = ?";
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            book = mapBook(resultSet);
            if (resultSet.getLong("author_id") != 0) {
                book.getAuthors().add(mapAuthor(resultSet));
            }
            while (resultSet.next()) {
                book.getAuthors().add(mapAuthor(resultSet));
            }
        }  else {
            throw new SQLException("Не удалось найти книгу с id " + id);
        }

        return book;
    }

    @Override
    public List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM book";
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            books.add(mapBook(resultSet));
        }
        return books;
    }

    @Override
    public Book update(Long id, Book book) throws SQLException {
        String sql = "UPDATE book SET title = ?, author_id = ? WHERE id = ?";
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, book.getTitle());
        statement.setLong(2, book.getAuthorId());
        statement.setLong(3, id);

        statement.executeUpdate();
        book.setId(id);
        return book;
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        String sql = "DELETE FROM book WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);

        statement.executeUpdate();
    }

    private Book mapBook(ResultSet resultSet) throws SQLException {
        return Book.builder()
                .id(resultSet.getLong("id"))
                .title(resultSet.getString("title"))
                .authorId(resultSet.getLong("author_id"))
                .build();
    }

    private Author mapAuthor(ResultSet resultSet) throws SQLException {
        return Author.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .secondName(resultSet.getString("second_name"))
                .build();
    }
}
