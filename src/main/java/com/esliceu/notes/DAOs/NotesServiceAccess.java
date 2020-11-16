package com.esliceu.notes.DAOs;

import com.esliceu.notes.Models.Notes;
import com.esliceu.notes.Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotesServiceAccess implements NoteDAO {
    @Override
    public List<Notes> getNotes(int userId, int pagina, int total) {
        List<Notes> notes = new ArrayList<>();

        try{

            Connection c = Database.getConnection();
            c.createStatement().execute("PRAGMA foreign_keys = ON");
            assert c != null;
            int start = pagina * total - total;
            PreparedStatement ps = c.prepareStatement("SELECT * FROM notes WHERE noteOwner = ? LIMIT ?, ?");
            ps.setInt(1, userId);
            ps.setInt(2, start);
            ps.setInt(3, total);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                int id = rs.getInt("noteId");
                int owner = rs.getInt("noteOwner");
                String title = rs.getString("noteTitle");
                String content = rs.getString("noteContent");
                String createdAt = rs.getString("createdAt");
                String updatedAt = rs.getString("updatedAt");

                // cream un objecte amb els resultats de la base de dades
                Notes note = new Notes(
                        id,
                        owner,
                        title,
                        content,
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
            c.createStatement().execute("PRAGMA foreign_keys = ON");

            assert c!=null;
            PreparedStatement ps = c.prepareStatement("INSERT INTO notes (noteOwner, noteTitle, noteContent, createdAt, updatedAt) VALUES (?, ?, ?, datetime('now'), datetime('now'))");
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

            Notes n = getNoteFromId(noteId);

            if(!n.getTitle().equals(title) && !n.getContent().equals(content)) {

                PreparedStatement ps = c.prepareStatement("UPDATE notes SET noteTitle = ? AND noteContent = ? WHERE noteId = ?");
                ps.setString(1, title);
                ps.setString(2, content);
                ps.setInt(3, noteId);
                ps.executeUpdate();
                ps.close();
                return true;

            } else if (n.getTitle().equals(title) && !n.getContent().equals(content)) {

                PreparedStatement ps = c.prepareStatement("UPDATE notes SET noteContent = ? WHERE noteId = ?");
                ps.setString(1, content);
                ps.setInt(2, noteId);
                ps.executeUpdate();
                ps.close();
                return true;

            } else if (!n.getTitle().equals(title) && n.getContent().equals(content)) {

                PreparedStatement ps = c.prepareStatement("UPDATE notes SET noteTitle = ?WHERE noteId = ?");
                ps.setString(1, title);
                ps.setInt(2, noteId);
                ps.executeUpdate();
                ps.close();
                return true;

            } else {
                return true;
            }
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteNote(int noteId) {
        try{

            Connection c = Database.getConnection();
            c.createStatement().execute("PRAGMA foreign_keys = ON");

            assert c != null;

            PreparedStatement ps = c.prepareStatement("DELETE FROM notes WHERE noteId = ?");
            ps.setInt(1, noteId);
            ps.execute();
            ps.close();

            PreparedStatement ps2 = c.prepareStatement("DELETE FROM noteSharing WHERE noteId = ?");
            ps2.setInt(1, noteId);
            ps2.execute();
            ps2.close();

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

        }catch (Exception e){
            return null;
        }

        return sharedNotes;
    }

    @Override
    public boolean shareNote(int noteId, int ownerId, int userId) {
        try{
            Connection c = Database.getConnection();
            c.createStatement().execute("PRAGMA foreign_keys = ON");
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
        Notes note = new Notes (0, 0, "", "", "", "");
        try{
            Connection c = Database.getConnection();

            c.createStatement().execute("PRAGMA foreign_keys = ON");
            assert c != null;

            PreparedStatement ps = c.prepareStatement("SELECT * FROM notes WHERE noteId = ?");
            ps.setInt(1, noteId);
            ResultSet rs = ps.executeQuery();

            note.setNoteId(rs.getInt("noteId"));
            note.setOwner(rs.getInt("noteOwner"));
            note.setTitle(rs.getString("noteTitle"));
            note.setContent(rs.getString("noteContent"));
            note.setCreatedAt(rs.getString("createdAt"));
            note.setUpdatedAt(rs.getString("updatedAt"));

            ps.close();

            return note;

        }catch (Exception e){
            return null;
        }
    }

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

        }catch (Exception e) {
            e.getCause();
        }

        return nRows;
    }
}
