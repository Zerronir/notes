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

@WebServlet(value = "/viewNote")
public class ViewNoteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/viewNote.jsp");
        HttpSession session = req.getSession();
        User uLogged = (User) session.getAttribute("user");
        int noteId = Integer.parseInt(req.getParameter("noteId"));

        if(uLogged != null){
            if(noteId > 0){

                session.setAttribute("user", uLogged);

                NotesService ns = new NotesServiceImpl();
                Notes note = ns.getNoteFromId(noteId);

                MutableDataSet options = new MutableDataSet();
                options.set(HtmlRenderer.SOFT_BREAK, "<br /> \n");
                Parser parser = Parser.builder(options).build();
                HtmlRenderer renderer = HtmlRenderer.builder(options).build();
                Node doc = parser.parse(note.getContent());
                String html = renderer.render(doc);

                req.setAttribute("note", note);
                req.setAttribute("content", html);
                dispatcher.forward(req, resp);

            } else {
                session.setAttribute("errD", "La nota no existeix");
                resp.sendRedirect(req.getContextPath() + "/notes");
            }

        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
            session.invalidate();
        }




    }
}
