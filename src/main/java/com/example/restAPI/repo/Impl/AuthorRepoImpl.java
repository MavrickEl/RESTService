package com.example.restAPI.repo.Impl;

import com.example.restAPI.entity.Author;
import com.example.restAPI.entity.Book;
import com.example.restAPI.repo.AuthorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.restAPI.utils.DBUtil.*;

public class AuthorRepoImpl implements AuthorRepository {

    @Override
    public Author save(Author author) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        String sql = "INSERT INTO author (name, second_name) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, author.getName());
        statement.setString(2, author.getSecondName());

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Не удалось создать автора");
        }

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            author.setId(generatedKeys.getLong(1));
        } else {
            throw new SQLException("Не удалось создать автора, идентификатор не получен");
        }
        return author;
    }

    @Override
    public Author findById(Long id) throws SQLException {
        Author author;
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        String sql = "SELECT a.*, b.id AS book_id, title " +
                "FROM author a " +
                "LEFT JOIN book b ON a.id = b.author_id " +
                "WHERE a.id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            author = mapAuthor(resultSet);
            if (resultSet.getLong("book_id") != 0) {
                author.getBooks().add(mapBook(resultSet));
            }
            while (resultSet.next()) {
                author.getBooks().add(mapBook(resultSet));
            }
        } else {
            throw new SQLException("Не удалось найти автора с id " + id);
        }

        return author;
    }

    @Override
    public List<Author> findAll() throws SQLException {
        List<Author> authors = new ArrayList<>();

        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        String sql = "SELECT a.*,  b.id AS book_id, title " +
                "FROM author a " +
                "LEFT JOIN book b ON a.id = b.author_id";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        Map<Long, Author> authorMap = new HashMap<>();
        while (resultSet.next()) {
            Long authorId = resultSet.getLong("id");
            Author author = authorMap.get(authorId);
            if (author == null) {
                author = mapAuthor(resultSet);
                authors.add(author);
                authorMap.put(authorId, author);
            }
            if (resultSet.getLong("book_id") != 0) {
                author.getBooks().add(mapBook(resultSet));
            }
        }
        return authors;
    }

    @Override
    public Author update(Long id, Author author) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        String sql = "UPDATE author SET name = ?, second_name = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, author.getName());
        statement.setString(2, author.getSecondName());
        statement.setLong(3, id);
        statement.executeUpdate();

        author.setId(id);
        return author;
    }


    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        String sql = "DELETE FROM author WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);

        statement.executeUpdate();
    }

    private Author mapAuthor(ResultSet resultSet) throws SQLException {
        return Author.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .secondName(resultSet.getString("second_name"))
                .books(new ArrayList<>())
                .build();
    }

    private Book mapBook(ResultSet resultSet) throws SQLException {
        return Book.builder()
                .id(resultSet.getLong("book_id"))
                .title(resultSet.getString("title"))
                .build();
    }

}
