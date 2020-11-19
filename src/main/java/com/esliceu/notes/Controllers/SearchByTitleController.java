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
            int total = 10;
            int start = 0;
            if(req.getParameter("start") != null){
                start = Integer.parseInt(req.getParameter("start"));
            } else {
                start = 1;
            }
            NotesService ns = new NotesServiceImpl();
            List<Notes> notesList = ns.titleSearch(title, start, total);
            int fileresText = ns.getTitleRows(title);
            int paginesTitle = fileresText / total;

            int pagesToView = comptadorPerText(paginesTitle, total);
            if(notesList != null){
                req.setAttribute("pagines", pagesToView);
                req.setAttribute("pageId", start);
                req.setAttribute("total", total);
                req.setAttribute("notes", notesList);
                session.setAttribute("user", uLogged);
                dispatcher.forward(req, resp);
            }

        } else {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
        }


    }
    private int comptadorPerText(int filas, int total){
        int pagines = filas;

        if (pagines % total > 0){
            pagines++;
        }

        return pagines;
    }
}
