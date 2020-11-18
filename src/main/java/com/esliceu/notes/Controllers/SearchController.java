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

@WebServlet(value = "/search")
public class SearchController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/searchResults.jsp");
        User uLogged = (User) session.getAttribute("user");

        if(uLogged != null){

        } else {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/searchResults.jsp");
        User uLogged = (User) session.getAttribute("user");

        if(uLogged != null){

            // Rebem les dades i decidim que feim
            String text = req.getParameter("title");
            String since = req.getParameter("dateInit");
            String toDate = req.getParameter("dateEnd");




        } else {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
        }

    }
}
