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

        NotesService ns = new NotesServiceImpl();
        int noteId = Integer.parseInt(req.getParameter("noteId"));
        int id = uLogged.getId();

        // Comprovam la validació de l'id de la nota i d'usuari
        if(ns.deleteSharedNote(noteId, id)){
            resp.sendRedirect(req.getContextPath() + "/notesCompartides");
        } else {
            String err = "No s'ha pogut descompartir aquesta nota amb tú, torna-ho a provar";
            session.setAttribute("errDS", err);
            resp.sendRedirect(req.getContextPath() + "/notesCompartides");
        }

    }
}
