package com.esliceu.notes.DAOs;

import com.esliceu.notes.Models.Notes;
import com.esliceu.notes.Models.User;

import java.util.List;

public interface NoteDAO {
    List<Notes> getNotes(int userId, int pagina, int total);

    boolean addNote(int owner, String title, String content);

    boolean updateNote(String title, String content, int noteId);

    boolean deleteNote(int noteId);

    List<Notes> getSharedWithMe(int userId, int pagina, int total);

    boolean shareNote(int noteId, int ownerId, int userId);

    boolean deleteSharedNote(int noteId, int userId);

    Notes getNoteFromId(int noteId);

    List<Notes> titleSearch(String title);

    List<Notes> dateSearch (String init, String end);

    int getRows(int userId);

    int getSharedRows(int userId);
}
