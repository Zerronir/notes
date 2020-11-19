package com.esliceu.notes.DAOs;

import com.esliceu.notes.Models.Notes;
import com.mysql.cj.protocol.Resultset;
import com.sun.tools.javac.util.StringUtils;
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacterExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesServiceAccess implements NoteDAO {
    @Override
    public List<Notes> getNotes(int userId, int pagina, int total) {
        List<Notes> notes = new ArrayList<>();

        try{

            Connection c = Database.getConnection();

            assert c != null;
            int start = pagina * total - total;
            PreparedStatement ps = c.prepareStatement("SELECT * FROM notes WHERE noteOwner = ? LIMIT ?, ?");
            ps.setInt(1, userId);
            ps.setInt(2, start);
            ps.setInt(3, total);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String text = render(rs.getString("noteContent"));
                String preview = text.substring(0, 20);
                String renderedPreview = render(preview);
                int id = rs.getInt("noteId");
                int owner = rs.getInt("noteOwner");
                String title = rs.getString("noteTitle");
                String createdAt = rs.getString("createdAt");
                String updatedAt = rs.getString("updatedAt");

                // cream un objecte amb els resultats de la base de dades
                Notes note = new Notes(
                        id,
                        owner,
                        title,
                        renderedPreview,
                        createdAt,
                        updatedAt
                );

                notes.add(note);

            }

            ps.close();

        }catch (Exception e){
            return null;
        }

        return notes;
    }

    @Override
    public boolean addNote(int owner, String title, String content) {

        try{
            Connection c = Database.getConnection();

            assert c!=null;
            PreparedStatement ps = c.prepareStatement("INSERT INTO notes (noteOwner, noteTitle, noteContent, createdAt, updatedAt) VALUES (?, ?, ?, DATE('NOW'), DATE('now'))");
            ps.setInt(1, owner);
            ps.setString(2, title);
            ps.setString(3, content);
            ps.execute();
            ps.close();

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateNote(String title, String content, int noteId) {
        try{

            Connection c = Database.getConnection();
            assert c!=null;

            PreparedStatement ps = c.prepareStatement("UPDATE notes SET noteTitle = ? AND noteContent = ? WHERE noteId = ?");
            ps.setString(1, title);
            ps.setString(2, content);
            ps.setInt(3, noteId);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteNote(int noteId) {
        try{

            Connection c = Database.getConnection();
            assert c != null;

            PreparedStatement ps = c.prepareStatement("DELETE FROM notes WHERE noteId = ?");
            ps.setInt(1, noteId);
            ps.execute();
            ps.close();

            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Notes> getSharedWithMe(int userId, int pagina, int total) {
        List<Notes> sharedNotes = new ArrayList<>();

        try {

            Connection c = Database.getConnection();
            assert c!=null;
            int start = pagina * total - total;
            PreparedStatement ps = c.prepareStatement("SELECT * FROM notes JOIN noteSharing nS on notes.noteId = nS.noteId WHERE nS.userId = ? LIMIT ?,?");
            ps.setInt(1, userId);
            ps.setInt(2, start);
            ps.setInt(3, total);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                int noteId = rs.getInt("noteId");
                int noteOwner = rs.getInt("noteOwner");
                String noteTitle = rs.getString("noteTitle");
                String noteContent = rs.getString("noteContent");
                String createdAt = rs.getString("createdAt");
                String updatedAt = rs.getString("updatedAt");

                Notes note = new Notes(noteId, noteOwner, noteTitle, noteContent, createdAt, updatedAt);
                sharedNotes.add(note);


            }

            ps.close();

        }catch (Exception e){
            return null;
        }

        return sharedNotes;
    }

    @Override
    public boolean shareNote(int noteId, int ownerId, int userId) {
        try{
            Connection c = Database.getConnection();
            assert c != null;

            PreparedStatement ps = c.prepareStatement("INSERT INTO noteSharing (noteId, ownerId, userId) VALUES (?, ?, ?)");
            ps.setInt(1, noteId);
            ps.setInt(2, ownerId);
            ps.setInt(3, userId);
            ps.execute();
            ps.close();

            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteSharedNote(int noteId, int userId) {

        try {
            Connection c = Database.getConnection();
            assert c!=null;

            PreparedStatement ps = c.prepareStatement("DELETE FROM noteSharing WHERE noteId = ? AND userId = ?");
            ps.setInt(1, noteId);
            ps.setInt(2, userId);
            ps.execute();
            ps.close();

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Notes getNoteFromId(int noteId) {
        try{
            Connection c = Database.getConnection();
            assert c != null;

            PreparedStatement ps = c.prepareStatement("SELECT * FROM notes WHERE noteId = ?");
            ps.setInt(1, noteId);
            ResultSet rs = ps.executeQuery();
            Notes note = new Notes(
                    rs.getInt("noteId"),
                    rs.getInt("noteOwner"),
                    rs.getString("noteTitle"),
                    rs.getString("noteContent"),
                    rs.getString("createdAt"),
                    rs.getString("updatedAt")
            );

            ps.close();

            return note;

        }catch (Exception e){
            return null;
        }
    }

    // CERCADORS
    @Override
    public List<Notes> titleSearch(String title, int start, int total){
        List<Notes> notesByTitle = new ArrayList<>();

        try{
            Connection c = Database.getConnection();
            assert c!= null;

            PreparedStatement ps = c.prepareStatement("SELECT * FROM notes WHERE noteTitle LIKE ? OR noteContent LIKE ?");
            ps.setString(1, "%" + title + "%");
            ps.setString(2, "%" + title + "%");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String text = render(rs.getString("noteContent"));
                String preview = text.substring(0, 10);
                String renderedPreview = render(preview);
                notesByTitle.add(new Notes(
                        rs.getInt("noteId"),
                        rs.getInt("noteTitle"),
                        rs.getString("noteTitle"),
                        renderedPreview,
                        rs.getString("createdAt"),
                        rs.getString("updatedAt")
                ));
            }




        }catch (SQLException e) {
            return null;
        }

        return notesByTitle;
    }

    @Override
    public List<Notes> dateSearch(String init, String end, int start, int total){
        List<Notes> notes = new ArrayList<>();
        try {

            Connection c = Database.getConnection();
            assert c != null;

            PreparedStatement ps = c.prepareStatement("SELECT * FROM notes WHERE createdAt BETWEEN ? AND ?");
            ps.setString(1, init);
            ps.setString(2, end);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                notes.add(new Notes(
                   rs.getInt("noteId"),
                   rs.getInt("noteOwner"),
                   rs.getString("noteTitle"),
                   render(rs.getString("noteContent")),
                   rs.getString("createdAt"),
                   rs.getString("updatedAt")
                ));
            }

        }catch (SQLException e) {
            return null;
        }

        return notes;
    }

    // CONTADORS PER LA PAGINACIO
    @Override
    public int getRows(int userId) {
        int nRows = 0;

        try{

            Connection c = Database.getConnection();
            assert c != null;

            PreparedStatement ps = c.prepareStatement("SELECT count(noteId) FROM notes WHERE noteOwner = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            nRows = rs.getInt(1);

            ps.close();

        }catch (Exception e) {
            e.getCause();
        }

        return nRows;
    }

    @Override
    public int getSharedRows(int userId) {
        int nRows = 0;

        try{

            Connection c = Database.getConnection();
            assert c != null;

            PreparedStatement ps = c.prepareStatement("SELECT count(noteId) FROM noteSharing WHERE userId = ? AND ownerId != ?");
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();

            nRows = rs.getInt(1);
            ps.close();

        }catch (Exception e) {
            e.getCause();
        }

        return nRows;
    }

    @Override
    public int rowsByTitle(String text) {
        int nRows = 0;

        try {

            Connection c = Database.getConnection();
            assert c!=null;

            PreparedStatement ps = c.prepareStatement("SELECT count(noteId) FROM notes WHERE noteTitle LIKE ? OR noteContent LIKE ?");
            ps.setString(1, "%s" + text + "%s");
            ps.setString(2, "%s" + text + "%s");
            ResultSet rs = ps.executeQuery();

            // Retornam el número de notes que ha trobat el cercador per texte per la seva paginació
            nRows = rs.getInt(1);
            ps.close();
        }catch (Exception e){
            e.getCause();
        }

        return nRows;
    }

    @Override
    public int rowsByDate(String init, String end) {
        int nRows = 0;

        try{

            Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT count(noteId) FROM notes WHERE createdAt BETWEEN ? AND ?");
            ps.setString(1, init);
            ps.setString(2, end);

            ResultSet rs = ps.executeQuery();

            nRows = rs.getInt(1);
            ps.close();

        }catch (Exception e){
            e.getCause();
        }

        return nRows;
    }


    private String render(String text) {
        MutableDataSet options = new MutableDataSet();
        options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node doc = parser.parse(text);
        String content = renderer.render(doc);
        return content;
    }


}
