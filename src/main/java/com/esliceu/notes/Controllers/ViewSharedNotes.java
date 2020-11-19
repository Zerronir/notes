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

@WebServlet(value = "/notesCompartides")
public class ViewSharedNotes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/viewSharedNotes.jsp");
        HttpSession session = req.getSession();
        User uLogged = (User) session.getAttribute("user");

        NotesService ns = new NotesServiceImpl();
        int start = 0;
        if(req.getParameter("page") != null){
            start = Integer.parseInt(req.getParameter("page"));
        } else {
            start = 1;
        }

        int total = 10;

        List<Notes> notesList = ns.getSharedWithMe(uLogged.getId(),start, total);
        req.setAttribute("shared", notesList);
        session.setAttribute("user", uLogged);

        dispatcher.forward(req,resp);

    }
}
