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

@WebServlet(value = "/editNote")
public class UpdateNoteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Carregam els parametres que necessitem per a que funcioni
        HttpSession session = req.getSession();
        User uLogged = (User) session.getAttribute("user");

        // Comprovam que l'usuari existeixi, si el resultat es null anirem al login i tancarem la sessió
        if(uLogged != null){
            // Rebem la id de la nota per url
            int noteId = Integer.parseInt(req.getParameter("noteId"));

            // Rebem les dades de la nota que ha creat l'usuari
            NotesService ns = new NotesServiceImpl();
            Notes note = ns.getNoteFromId(noteId);

            // Si obtenim resultat anirem al editor
            req.setAttribute("note", note);
            session.setAttribute("user", uLogged);
            RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/updateNote.jsp");
            // Retornam la vista que demanem al servidor
            dispatcher.forward(req, resp);

        } else {
            // En cas de que l'usuari no hagi iniciat sessió el retornarem al login
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Rebem els parametres que ncessitem per a fer l'update
        HttpSession session = req.getSession();
        User uLogged = (User) session.getAttribute("user");

        if(uLogged != null){

            // Obetnim les dades de la nota que hem actualitzat
            int noteId = Integer.parseInt(req.getParameter("noteId"));
            String title = req.getParameter("noteTitle");
            String content = req.getParameter("content");

            if(validateNote(title, content)){

                session.setAttribute("user", uLogged);
                resp.sendRedirect(req.getContextPath() + "/notes");

            } else {
                String errUp = "El títol o el contigut no es vàlid, per favor torna-ho a provar";
                req.setAttribute("noteId", noteId);
                req.setAttribute("errUp", errUp);
                resp.sendRedirect(req.getContextPath() + "/editNote");
            }


        } else {
            // En cas de no tenir un usuari valid l'enviarem al login
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
        }


    }

    // Funció per a validar la nota
    private boolean validateNote(String title, String content) {

        if((title.length() > 5 && title.length() <= 50) && content.length() > 10){
            return true;
        }

        return false;
    }
}
