package com.esliceu.notes.Controllers;

import com.esliceu.notes.Models.Notes;
import com.esliceu.notes.Models.User;
import com.esliceu.notes.Services.NotesService;
import com.esliceu.notes.Services.NotesServiceImpl;
import com.esliceu.notes.Services.UserService;
import com.esliceu.notes.Services.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "")
public class MainPageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User uLogged = (User) session.getAttribute("user");
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/index.jsp");


        if (uLogged != null){

            int start = 0;
            if(req.getParameter("page") != null){
                start = Integer.parseInt(req.getParameter("page"));
            } else {
                start = 1;
            }

            int total = 5;

            UserService us = new UserServiceImpl();
            NotesService ns = new NotesServiceImpl();
            List<Notes> notes = ns.getNotes(uLogged.getId(), start, total);
            int fileres = ns.getRows(uLogged.getId());

            int pagines = fileres / total;

            if(pagines % total > 0){
                pagines++;
            }

            req.setAttribute("pagines", pagines);
            req.setAttribute("pageId", start);
            req.setAttribute("total", total);

            List<User> userList = us.getAll(uLogged.getId());
            req.setAttribute("users", userList);
            req.setAttribute("notes", notes);
            session.setAttribute("user", uLogged);

            dispatcher.forward(req, resp);


        } else {

            resp.sendRedirect(req.getContextPath() + "/login");

        }



    }
}
