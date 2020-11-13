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

@WebServlet(value = "/deleteNote")
public class DeleteNoteController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User uLogged = (User) session.getAttribute("user");
        int noteId = Integer.parseInt(req.getParameter("noteId"));

        if(uLogged != null){
            if(noteId > 0) {

                NotesService ns = new NotesServiceImpl();

                if(ns.deleteNote(noteId)){
                    session.setAttribute("delSucs", "S'ha eliminat correctament la nota");
                    resp.sendRedirect(req.getContextPath() + "/notes");
                } else {
                    session.setAttribute("errD", "No s'ha pogut eliminar la nota");
                    resp.sendRedirect(req.getContextPath() + "/notes");
                }

            } else {
                session.setAttribute("errD", "La nota no existeix");
                resp.sendRedirect(req.getContextPath() + "/notes");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }


    }
}
