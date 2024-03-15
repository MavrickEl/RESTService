package com.example.restAPI.controller;

import com.example.restAPI.dto.AuthorRequest;
import com.example.restAPI.service.AuthorService;
import com.example.restAPI.service.Impl.AuthorServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/author")
public class AuthorController extends HttpServlet {
    private AuthorService authorService = new AuthorServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = initResp(resp, HttpServletResponse.SC_OK).getWriter();
        String id = req.getParameter("id");
        if (id != null) {
            if (!isValid(id)) {
                initResp(resp, HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Пустой параметр");
                return;
            }
            try {
                Long.parseLong(id);
            } catch (NumberFormatException e) {
                initResp(resp, HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Невалидный формат id");
                return;
            }
            try {
                out.print(new Gson().toJson(authorService.getById(Long.valueOf((req.getParameter("id"))))));
            } catch (SQLException e) {
                initResp(resp, HttpServletResponse.SC_BAD_REQUEST).getWriter();
                out.print(e.getMessage());
            }
        } else {
            try {
                out.print(authorService.getAll().stream().map(authorResponse -> new Gson().toJson(authorResponse)).toList());
            } catch (SQLException e) {
                initResp(resp, HttpServletResponse.SC_NO_CONTENT).getWriter();
                out.print(e.getMessage());
            }
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = initResp(resp, HttpServletResponse.SC_CREATED).getWriter();
        String name = req.getParameter("name");
        String secondName = req.getParameter("second_name");

        if (isValid(name) && isValid(secondName)) {
            AuthorRequest authorRequest = AuthorRequest.builder().name(name).secondName(secondName).build();
            try {
                authorService.save(authorRequest);
                out.print("Успешно добавлено");
            } catch (SQLException e) {
                out = initResp(resp, HttpServletResponse.SC_BAD_REQUEST).getWriter();
                out.print(e.getMessage());
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("Невалидные данные");
        }
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = initResp(resp, HttpServletResponse.SC_OK).getWriter();

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String secondName = req.getParameter("second_name");
        try {
            Long.parseLong(id);
        } catch (NumberFormatException e) {
            initResp(resp, HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Невалидный формат id");
            return;
        }
        if (isValid(id) && isValid(name) && isValid(secondName)) {
            AuthorRequest authorRequest = AuthorRequest.builder().id(Long.valueOf(id)).name(name).secondName(secondName).build();
            try {
                authorService.update(authorRequest);
                out.print("Успешно обновлено");
            } catch (SQLException e) {
                out = initResp(resp, HttpServletResponse.SC_BAD_REQUEST).getWriter();
                out.print(e.getMessage());
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Невалидные данные");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = initResp(resp, HttpServletResponse.SC_OK).getWriter();

        String id = req.getParameter("id");
        if (!isValid(id)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Пустой параметр id");
            return;
        }
        try {
            Long.parseLong(id);
        } catch (NumberFormatException e) {
            initResp(resp, HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Невалидный формат id");
            return;
        }
        try {
            authorService.delete(Long.valueOf(id));
            out.println("Успешно удалено");
        } catch (SQLException e) {
            out = initResp(resp, HttpServletResponse.SC_BAD_REQUEST).getWriter();
            out.print(e.getMessage());

        }

    }

    private static HttpServletResponse initResp(HttpServletResponse resp, int status) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(status);
        return resp;
    }

    private static boolean isValid(String parameter) {
        return parameter != null && !parameter.isEmpty();
    }
}

