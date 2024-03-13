package com.example.restAPI.controller;

import com.example.restAPI.dto.BookRequest;
import com.example.restAPI.service.BookService;
import com.example.restAPI.service.Impl.BookServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/book")
public class BookController extends HttpServlet {
    private final BookService bookService = new BookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = initResp(resp, HttpServletResponse.SC_OK).getWriter();
        if (req.getParameter("id") != null) {
            String bookJson = null;
            try {
                bookJson = new Gson().toJson(bookService.getById(Long.valueOf((req.getParameter("id")))));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            out.print(bookJson);

        } else {
            try {
                out.print(bookService.getAll().stream().map(bookResponse -> new Gson().toJson(bookResponse)).toList());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = req.getParameter("title");
        Long authorId = Long.valueOf(req.getParameter("author_id"));

        BookRequest bookRequest = BookRequest.builder().title(title).authorId(authorId).build();

        PrintWriter out = initResp(resp, HttpServletResponse.SC_CREATED).getWriter();
        try {
            out.print(bookService.save(bookRequest));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        out.flush();

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        String title = req.getParameter("title");
        Long authorId = Long.valueOf(req.getParameter("author_id"));

        BookRequest bookRequest = BookRequest.builder().title(title).authorId(authorId).build();
        try {
            bookService.update(authorId, bookRequest);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        PrintWriter writer = initResp(resp, HttpServletResponse.SC_OK).getWriter();
        writer.println("updated");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        try {
            bookService.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PrintWriter writer = initResp(resp, HttpServletResponse.SC_OK).getWriter();
        writer.println("deleted");
    }

    private static HttpServletResponse initResp(HttpServletResponse resp, int status) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(status);
        return resp;
    }
}

