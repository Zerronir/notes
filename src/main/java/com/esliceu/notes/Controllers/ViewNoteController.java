package com.esliceu.notes.Controllers;

import com.esliceu.notes.Models.Notes;
import com.esliceu.notes.Models.User;
import com.esliceu.notes.Services.NotesService;
import com.esliceu.notes.Services.NotesServiceImpl;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/viewNote")
public class ViewNoteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Rebem les dades que necessitem
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/viewNote.jsp");
        HttpSession session = req.getSession();
        User uLogged = (User) session.getAttribute("user");
        int noteId = Integer.parseInt(req.getParameter("noteId"));

        if(uLogged != null){

            if(noteId>0){


                NotesService ns = new NotesServiceImpl();
                // Rebem la nota desde el service
                Notes n = ns.getNoteFromId(noteId);

                //De la nota agafam el contingut y cambiam el markdown de la nota a html
                MutableDataSet options = new MutableDataSet();
                options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");
                Parser parser = Parser.builder(options).build();
                HtmlRenderer renderer = HtmlRenderer.builder(options).build();
                Node doc = parser.parse(n.getContent());
                String content = renderer.render(doc);


                // Ficam la nota dintre del request
                req.setAttribute("note", n);
                req.setAttribute("content", content);
                session.setAttribute("user", uLogged);

                // Una vegada tenim tots els elements preparats enviam la vista
                dispatcher.forward(req, resp);


            } else {
                resp.sendRedirect(req.getContextPath() + "/notes");
            }

        } else {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
        }


    }
}
