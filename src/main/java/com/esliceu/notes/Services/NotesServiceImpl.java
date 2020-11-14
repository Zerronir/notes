package com.esliceu.notes.Services;

import com.esliceu.notes.DAOs.NoteDAO;
import com.esliceu.notes.DAOs.NotesServiceAccess;
import com.esliceu.notes.Models.Notes;
import com.esliceu.notes.Models.User;

import java.util.List;

public class NotesServiceImpl implements NotesService {
    @Override
    public List<Notes> getNotes(int userId) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.getNotes(userId);
    }

    @Override
    public boolean addNote(Notes n) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.addNote(n);
    }

    @Override
    public boolean updateNote(String title, String content, int noteId) {
        return false;
    }

    @Override
    public boolean deleteNote(int noteId) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.deleteNote(noteId);
    }

    @Override
    public List<Notes> getSharedWithMe(int userId) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.getSharedWithMe(userId);
    }

    @Override
    public boolean shareNote(int noteId, int ownerId, int userId) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.shareNote(noteId, ownerId, userId);
    }

    @Override
    public void deleteSharedNote(Notes n, User u) {

    }

    @Override
    public Notes getNoteFromId(int noteId) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.getNoteFromId(noteId);
    }
}
