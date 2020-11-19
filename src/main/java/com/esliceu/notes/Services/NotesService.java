package com.esliceu.notes.Services;

import com.esliceu.notes.Models.Notes;
import com.esliceu.notes.Models.User;

import java.util.List;

public interface NotesService {

    List<Notes> getNotes(int userId, int pagina, int total);

    boolean addNote(int owner, String title, String content);

    boolean updateNote(String title, String content, int noteId);

    boolean deleteNote(int noteId);

    List<Notes> getSharedWithMe(int userId, int pagina, int total);

    boolean shareNote(int noteId, int ownerId, int userId);

    boolean deleteSharedNote(int noteId, int userId);

    Notes getNoteFromId(int noteId);

    List<Notes> titleSearch(String title, int start, int total);

    List<Notes> dateSearch(String init, String end, int start, int total);

    int getRows(int userId);

    int getSharedRows(int userId);

    int getTitleRows(String text);
    int getSearchRows(String init, String end);

}
