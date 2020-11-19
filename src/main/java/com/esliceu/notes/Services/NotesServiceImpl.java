package com.esliceu.notes.Services;

import com.esliceu.notes.DAOs.NoteDAO;
import com.esliceu.notes.DAOs.NotesServiceAccess;
import com.esliceu.notes.Models.Notes;

import java.util.List;

public class NotesServiceImpl implements NotesService {
    @Override
    public List<Notes> getNotes(int userId, int pagina, int total) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.getNotes(userId, pagina, total);
    }

    @Override
    public boolean addNote(int owner, String title, String content) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.addNote(owner, title, content);
    }

    @Override
    public boolean updateNote(String title, String content, int noteId) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.updateNote(title, content, noteId);
    }

    @Override
    public boolean deleteNote(int noteId) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.deleteNote(noteId);
    }

    @Override
    public List<Notes> getSharedWithMe(int userId, int pagina, int total) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.getSharedWithMe(userId, pagina, total);
    }

    @Override
    public boolean shareNote(int noteId, int ownerId, int userId) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.shareNote(noteId, ownerId, userId);
    }

    @Override
    public boolean deleteSharedNote(int noteId, int userId) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.deleteSharedNote(noteId, userId);
    }

    @Override
    public Notes getNoteFromId(int noteId) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.getNoteFromId(noteId);
    }

    @Override
    public List<Notes> titleSearch(String title, int start, int total) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.titleSearch(title, start, total);
    }

    @Override
    public List<Notes> dateSearch(String init, String end, int start, int total){
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.dateSearch(init, end, start, total);
    }

    @Override
    public int getRows(int userId) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.getRows(userId);
    }

    @Override
    public int getSharedRows(int userId) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.getSharedRows(userId);
    }

    @Override
    public int getTitleRows(String text) {
       NoteDAO nDAO = new NotesServiceAccess();
       return nDAO.rowsByTitle(text);
    }

    @Override
    public int getSearchRows(String init, String end) {
        NoteDAO nDAO = new NotesServiceAccess();
        return nDAO.rowsByDate(init, end);
    }
}
