package com.example.restAPI.repo.Impl;

import com.example.restAPI.entity.BookAuthor;
import com.example.restAPI.repo.BookAuthorRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.restAPI.utils.DBUtil.*;

public class BookAuthorRepoImpl implements BookAuthorRepository {
    @Override
    public void save(BookAuthor bookAuthor) throws SQLException {
        String sql = "INSERT INTO book_author (book_id, author_id) " +
                "VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, bookAuthor.getBookId());
            statement.setLong(2, bookAuthor.getAuthorId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Автор с id " + bookAuthor.getAuthorId() + " не существует");
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

    }

}
