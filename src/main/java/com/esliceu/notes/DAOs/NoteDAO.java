package com.esliceu.notes.DAOs;

import com.esliceu.notes.Models.Notes;
import com.esliceu.notes.Models.User;

import java.util.List;

public interface NoteDAO {
    List<Notes> getNotes(int userId);

    boolean addNote(Notes n);

    boolean updateNote(String title, String content, int noteId);

    boolean deleteNote(int noteId);

    List<Notes> getSharedWithMe(int userId);

    boolean shareNote(int noteId, int ownerId, int userId);

    void deleteSharedNote(Notes n, User u);

    Notes getNoteFromId(int noteId);
}
