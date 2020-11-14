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
    public List<Notes> getNotes(int userId) {
        List<Notes> notes = new ArrayList<>();

        try{

            Connection c = Database.getConnection();
            c.createStatement().execute("PRAGMA foreign_keys = ON");
            assert c != null;

            PreparedStatement ps = c.prepareStatement("SELECT * FROM notes WHERE noteOwner = ?");
            ps.setInt(1, userId);
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
    public boolean addNote(Notes n) {

        try{
            Connection c = Database.getConnection();
            c.createStatement().execute("PRAGMA foreign_keys = ON");

            assert c!=null;
            int owner = n.getOwner();
            String title = n.getTitle();
            String content = n.getContent();
            PreparedStatement ps = c.prepareStatement("INSERT INTO notes (noteOwner, noteTitle, noteContent, createdAt, updatedAt) VALUES (?, ?, ?, DATETIME('now'), datetime('now'))");
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
        return false;
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


            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Notes> getSharedWithMe(int userId) {
        List<Notes> sharedNotes = new ArrayList<>();

        try {

            Connection c = Database.getConnection();
            assert c!=null;

            PreparedStatement ps = c.prepareStatement("SELECT * FROM notes JOIN noteSharing nS on notes.noteId = nS.noteId WHERE nS.userId = ?");
            ps.setInt(1, userId);
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
    public void deleteSharedNote(Notes n, User u) {

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
}
