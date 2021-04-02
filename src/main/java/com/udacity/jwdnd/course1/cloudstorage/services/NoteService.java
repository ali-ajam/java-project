package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotes(Integer userId) {
        return this.noteMapper.listNotes(userId);
    }

    public Integer addNote(Note note) {
        return this.noteMapper.insert(note);
    }

    public void deleteNote(Integer noteId) {
        this.noteMapper.delete(noteId);
    }

    public void updateNote(Integer noteId, String noteTitle, String noteDescription, Integer userId) {
        Note updatedNote = new Note(noteId, noteTitle, noteDescription, userId);
        this.noteMapper.update(updatedNote);
    }
}
