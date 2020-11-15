package com.esliceu.notes.Controllers;

import com.esliceu.notes.Models.Notes;
import com.esliceu.notes.Models.User;
import com.esliceu.notes.Services.NotesService;
import com.esliceu.notes.Services.NotesServiceImpl;
import com.esliceu.notes.Services.UserService;
import com.esliceu.notes.Services.UserServiceImpl;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import scala.Int;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(value = "/notes")
public class NotesController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/notes.jsp");
        HttpSession session = req.getSession();
        User uLogged = (User) session.getAttribute("user");

        if(uLogged != null){

            int start = 0;
            if(req.getParameter("page") != null){
                start = Integer.parseInt(req.getParameter("page"));
            } else {
                start = 1;
            }

            int total = 10;

            int id = uLogged.getId();
            UserService us = new UserServiceImpl();
            NotesService ns = new NotesServiceImpl();
            List<Notes> notes = ns.getNotes(id, start, total);
            int fileres = ns.getRows(id);

            int pagines = fileres / total;

            if(pagines % total > 0){
                pagines++;
            }

            req.setAttribute("pagines", pagines);
            req.setAttribute("pageId", start);
            req.setAttribute("total", total);

            List<User> userList = us.getAll(uLogged.getId());
            req.setAttribute("users", userList);
            req.setAttribute("notes", notes);
            session.setAttribute("user", uLogged);

            dispatcher.forward(req, resp);

        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        // Rebem els atributs que necessitem per a crear la nota
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        // Rebem l'usuari del que obtindrem el seu id
        User uLogged = (User) session.getAttribute("user");

        if (uLogged != null){

            if(validateNote(title, content)){
                NotesService ns = new NotesServiceImpl();
                PrintWriter pw = resp.getWriter();

                Notes n = new Notes(0, uLogged.getId(), title, content, "", "");


                pw.println("note title" + n.getTitle());
                pw.println("note content" + n.getContent());
                pw.println("owner" + n.getOwner());

                // Si rebem un valor de true, la nota s'ha afegit correctament y redireccionarem a l'usuari a la pàgina de notes
                if(ns.addNote(uLogged.getId(), title, content)){
                    session.setAttribute("user", uLogged);
                    resp.sendRedirect(req.getContextPath() + "/notes");

                } else {
                    String err = "Hi ha hagut un error al inserir la base de dades, per favor, torna-ho a provar i si persisteix l'error contacta amb un administrador";
                    session.setAttribute("err", err);
                    resp.sendRedirect(req.getContextPath() + "/notes");
                }
            } else {
                String err = "Hi ha hagut un error amb el títol o amb el contigut, revisa les longituds";
                session.setAttribute("err", err);
                resp.sendRedirect(req.getContextPath() + "/notes");
            }


        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
            session.invalidate();
        }

    }

    private boolean validateNote(String title, String content) {

        if((title.length() > 5 && title.length() <= 50) && content.length() > 10){
            return true;
        }

        return false;
    }


}
