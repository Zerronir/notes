package com.esliceu.notes.Controllers;

import com.esliceu.notes.Models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "")
public class MainPageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User uLogged = (User) session.getAttribute("user");
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/index.jsp");
        dispatcher.forward(req, resp);
    }
}
