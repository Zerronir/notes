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

@WebServlet(value = "/shareNote")
public class ShareNoteController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        PrintWriter pw = resp.getWriter();
        User uLogged = (User) session.getAttribute("user");
        int noteId = Integer.parseInt(req.getParameter("noteId"));
        int userId = Integer.parseInt(req.getParameter("usersList"));
        if(uLogged != null) {

            NotesService ns = new NotesServiceImpl();

            if(ns.shareNote(noteId, uLogged.getId(), userId)) {
                pw.println("NO ERRORS: ");
                pw.println("noteId: " + noteId);
                pw.println("ownerId: " + uLogged.getId());
                pw.println("userId: " + userId);
                session.setAttribute("sharedOk", "La nota s'ha compartit correctament");
                //resp.sendRedirect(req.getContextPath() + "/notes");
            } else {
                pw.println("WITH ERRORS:");
                pw.println("noteId: " + noteId);
                pw.println("ownerId: " + uLogged.getId());
                pw.println("userId: " + userId);
                session.setAttribute("sharedErr", "La nota no s'ha compartit");
                //resp.sendRedirect(req.getContextPath() + "/notes");
            }


        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }

    }
}
