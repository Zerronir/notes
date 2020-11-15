package com.esliceu.notes.Controllers;

import com.esliceu.notes.Models.User;
import com.esliceu.notes.Services.NotesService;
import com.esliceu.notes.Services.NotesServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/deleteSharedNoteNotOwner")
public class DeleteSharedWithMe extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User uLogged = (User) session.getAttribute("user");

        if(uLogged != null){

            int noteId = Integer.parseInt(req.getParameter("noteId"));

            NotesService ns = new NotesServiceImpl();
            int id = uLogged.getId();

            PrintWriter pw = resp.getWriter();
            pw.println(noteId);
            pw.println(uLogged.getId());

            // Comprovam la validació de l'id de la nota i d'usuari
            if(ns.deleteSharedNote(noteId, id)){
                session.setAttribute("user", uLogged);
                resp.sendRedirect(req.getContextPath() + "/notesCompartides");
            } else {
                String err = "No s'ah pogut descompartir aquesta nota amb tú, torna-ho a provar";
                session.setAttribute("errDS", err);
                resp.sendRedirect(req.getContextPath() + "/notesCompartides");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }

    }
}
