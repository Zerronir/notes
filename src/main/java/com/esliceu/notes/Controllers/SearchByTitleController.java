package com.esliceu.notes.Controllers;

import com.esliceu.notes.Models.Notes;
import com.esliceu.notes.Models.User;
import com.esliceu.notes.Services.NotesService;
import com.esliceu.notes.Services.NotesServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/searchByTitle")
public class SearchByTitleController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/searchResults.jsp");
        HttpSession session = req.getSession();
        User uLogged = (User) session.getAttribute("user");

        if(uLogged != null){
            String title = req.getParameter("title");

            NotesService ns = new NotesServiceImpl();
            List<Notes> notesList = ns.titleSearch(title);

            if(notesList != null){
                req.setAttribute("notes", notesList);
                session.setAttribute("user", uLogged);
                dispatcher.forward(req, resp);
            }

        } else {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
        }


    }
}
