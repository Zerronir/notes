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
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(value = "/search")
public class SearchController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/searchForm.jsp");
        User uLogged = (User) session.getAttribute("user");

        if(uLogged != null){
            session.setAttribute("user", uLogged);
            dispatcher.forward(req, resp);
        } else {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/searchResults.jsp");
        User uLogged = (User) session.getAttribute("user");
        PrintWriter pw = resp.getWriter();

        if(uLogged != null){

            // Rebem les dades i decidim que feim
            String text = req.getParameter("text");
            String since = req.getParameter("start");
            String toDate = req.getParameter("final");

            pw.println(since);
            pw.println(toDate);
            pw.println(text);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            String initToDate = format.format(new Date());
            try {
                Date initDate = format.parse(since);
                Date endDate = format.parse(toDate);

                pw.println(initDate);
                pw.println(endDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (text != null) {

                NotesService ns = new NotesServiceImpl();
                List<Notes> notes = ns.titleSearch(text);

                // Cream el comptador de págines per texte
                int total = 10;


                req.setAttribute("notes", notes);
                session.setAttribute("user", uLogged);
                dispatcher.forward(req, resp);

            } else {
                NotesService ns = new NotesServiceImpl();
                List<Notes> notes = ns.dateSearch(since, toDate);
                req.setAttribute("notes", notes);
                session.setAttribute("user", uLogged);
                dispatcher.forward(req, resp);
            }


        } else {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
        }

    }

    private int comptadorPerText(int filas, int total){
        int pagines = 0;

        if (pagines % 2 == 0){
            pagines++;
        }

        return pagines;
    }

    private int comptadorPerData(int filasFecha, int total){
        int pagines = 0;

        if (pagines % 2 == 0){
            pagines++;
        }

        return pagines;
    }


}
