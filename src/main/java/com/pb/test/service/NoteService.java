package com.pb.test.service;

import com.pb.test.exception.NoteNotFoundException;
import com.pb.test.exception.ZeroLikesException;
import com.pb.test.model.Note;
import com.pb.test.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    public List<Note> getAll() {
        var notes = noteRepository.findAll();
        Collections.reverse(notes);
        return notes;
    }

    public Note getNote(String id) {
        return noteRepository.findById(id).orElseThrow(NoteNotFoundException::new);
    }

    public Note saveNote(@NotNull Note note) {
        return noteRepository.save(note);
    }

    public Note updateNote(String id, @NotNull Note note) {
        Note noteToUpdate = noteRepository.findById(id).orElseThrow(NoteNotFoundException::new);
        noteToUpdate.setContent(note.getContent());
        noteToUpdate.setLikes(note.getLikes());

        noteRepository.deleteById(id);
        return noteRepository.save(noteToUpdate);
    }

    public void deleteAll() {
        noteRepository.deleteAll();
    }

    public void deleteNote(String id) {
        noteRepository.deleteById(id);
    }

    public Note increaseLikesInNote(String id) {
        Note noteToUpdate = noteRepository.findById(id).orElseThrow(NoteNotFoundException::new);
        noteToUpdate.setLikes(noteToUpdate.getLikes() + 1);

        noteRepository.deleteById(id);
        return noteRepository.save(noteToUpdate);
    }

    public Note decreaseLikesInNote(String id) {
        Note noteToUpdate = noteRepository.findById(id).orElseThrow(NoteNotFoundException::new);

        if (noteToUpdate.getLikes() > 0) {
            noteToUpdate.setLikes(noteToUpdate.getLikes() - 1);
        } else {
            throw new ZeroLikesException();
        }


        noteRepository.deleteById(id);
        return noteRepository.save(noteToUpdate);
    }
}
