package com.esliceu.notes.Controllers;

import com.esliceu.notes.Models.User;
import com.esliceu.notes.Services.UserService;
import com.esliceu.notes.Services.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(value = "/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Setting up values for loggin the user
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if(emailCheck(email)){

            if(checkPass(password, password)){

                try {

                    String passtoDB = hashPass(password);

                    UserService us = new UserServiceImpl();
                    User uLogged = us.getFromLogin(email, passtoDB);

                    if(uLogged != null){
                        session.setAttribute("user", uLogged);
                        session.setAttribute("err", "");
                        resp.sendRedirect(req.getContextPath() + "/user");

                    } else {
                        session.setAttribute("err", "No existeix l'usuari que has introduït, revisa les dades i torna-ho a provar");
                        resp.sendRedirect(req.getContextPath() + "/login");
                    }

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            } else {
                String err = "La contrasenya no compleix els requisits de seguretat";
                session.setAttribute("err", err);
                resp.sendRedirect(req.getContextPath() + "/login");
            }

        } else {
            String err = "El correu electrònic no compleix amb els requisits de seguretat o no es un correu vàlid";
            session.setAttribute("err", err);
            resp.sendRedirect(req.getContextPath() + "/login");
        }


    }


    private boolean emailCheck(String s) {

        int arrova = s.indexOf("@");
        boolean end = s.endsWith(".com");
        boolean end2 = s.endsWith(".es");
        boolean end3 = s.endsWith(".net");
        boolean end4 = s.endsWith(".org");

        if((arrova != -1 && arrova != 0) && end){
            return true;
        }

        if((arrova != -1 && arrova != 0) && end2){
            return true;
        }

        if((arrova != -1 && arrova != 0) && end3){
            return true;
        }

        if((arrova != -1 && arrova != 0) && end4){
            return true;
        }

        return false;
    }

    private boolean checkPass(String s, String s1){
        /**
         *
         * La nostra contrasenya ha de tenir:
         * Al manco un número
         * Al manco una lletra minúscula
         * Al manco una lletra majúscula
         * Al manco un caracter especial
         * Ha de tenir una llargària de entre 5 y 50 lletres
         *
         * */

        if(s.equals(s1)){
            String regex = "^(?=.*[0-9])"
                    + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[@#$%^&+=])"
                    + "(?=\\S+$).{5,50}$";

            Pattern p = Pattern.compile(regex);

            Matcher matcher = p.matcher(s);

            return matcher.matches();
        }

        return false;
    }

    private String hashPass(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(password.getBytes());

        byte[] digest = md.digest();

        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < digest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & digest[i]));
        }
        return hexString.toString();

    }
}
