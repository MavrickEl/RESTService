package com.example.restAPI.controller;

import com.example.restAPI.dto.AuthorRequest;
import com.example.restAPI.dto.AuthorResponse;
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
    private final AuthorService authorService = new AuthorServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String authorJson;
        PrintWriter out;
        String id = req.getParameter("id");
        if (id != null) {
            if (!isValid(id)) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Пустой параметр");
                return;
            }
            try {
                authorJson = new Gson().toJson(authorService.getById(Long.valueOf((req.getParameter("id")))));
                out = initResp(resp, HttpServletResponse.SC_OK).getWriter();
            } catch (SQLException e) {
                authorJson = e.getMessage();
                out = initResp(resp, HttpServletResponse.SC_BAD_REQUEST).getWriter();
            }
        } else {
            try {
                authorJson = String.valueOf(authorService.getAll().stream().map(authorResponse -> new Gson().toJson(authorResponse)).toList());
                out = initResp(resp, HttpServletResponse.SC_OK).getWriter();
            } catch (SQLException e) {
                authorJson = e.getMessage();
                out = initResp(resp, HttpServletResponse.SC_NO_CONTENT).getWriter();
            }
        }
        out.print(authorJson);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String secondName = req.getParameter("second_name");
        if (isValid(name) && isValid(secondName)) {
            AuthorRequest authorRequest = AuthorRequest.builder().name(name).secondName(secondName).build();
            AuthorResponse authorResponse;
            PrintWriter out;
            try {
                authorResponse = authorService.save(authorRequest);
                out = initResp(resp, HttpServletResponse.SC_CREATED).getWriter();
                out.print(new Gson().toJson(authorResponse));
            } catch (SQLException e) {
                out = initResp(resp, HttpServletResponse.SC_CREATED).getWriter();
                out.print(e.getMessage());
            }
            out.flush();
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Необходимые параметры отсутствуют или являются пустыми");
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idString = req.getParameter("id");
        String name = req.getParameter("name");
        String secondName = req.getParameter("second_name");
        if (isValid(idString) && isValid(name) && isValid(secondName)) {
            Long id = Long.valueOf(idString);

            AuthorRequest authorRequest = AuthorRequest.builder().name(name).secondName(secondName).build();
            AuthorResponse authorResponse;
            PrintWriter out;
            try {
                authorResponse = authorService.update(id, authorRequest);
                out = initResp(resp, HttpServletResponse.SC_OK).getWriter();
                out.print(new Gson().toJson(authorResponse));
            } catch (SQLException e) {
                out = initResp(resp, HttpServletResponse.SC_BAD_REQUEST).getWriter();
                out.print(e.getMessage());
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Необходимые параметры отсутствуют или являются пустыми");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!isValid(req.getParameter("id"))) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Пустой параметр");
            return;
        }
        Long id = Long.valueOf(req.getParameter("id"));
        PrintWriter out;
        try {
            authorService.delete(id);
            out = initResp(resp, HttpServletResponse.SC_OK).getWriter();
            out.println("Автор успешно удален");
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

