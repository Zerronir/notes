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

@WebServlet(value = "/deleteMultipleNotes")
public class deleteMultipleNotes extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Rebem els parametres que necessitam
        String array[] = req.getParameterValues("notesToDelete[]");
        HttpSession session = req.getSession();
        PrintWriter pw = resp.getWriter();
        boolean result = false;
        User uLogged = (User) session.getAttribute("user");

        // Cream els servicis per a les notes
        NotesService ns = new NotesServiceImpl();

        // Realitzam un bucle per a eliminar notes de manera múltiple en funció de la longitud de l'array
        for (int i = 0; i < array.length; i++) {
            result = ns.deleteNote(Integer.parseInt(array[i]));
        }

        // Si les notes s'han eliminat correctament podem tornar a la vista de notes
        if(result){
            session.setAttribute("user", uLogged);
            resp.sendRedirect(req.getContextPath() + "/notes");
        } else {
            pw.println("error");
        }

    }
}
