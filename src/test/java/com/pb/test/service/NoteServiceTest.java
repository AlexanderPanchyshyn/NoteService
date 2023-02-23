package com.pb.test.service;

import com.pb.test.exception.NoteNotFoundException;
import com.pb.test.model.entity.Note;
import com.pb.test.repository.NoteRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;
    @InjectMocks
    private NoteService noteService;

    @Test
    public void whenSavingNoteShouldSave(){
        Note note = new Note();
        Mockito.when(noteRepository.save(note)).thenReturn(note);
        Note savedNote = noteService.saveNote(note);
        Assertions.assertEquals(savedNote,note);
        verify(noteRepository).save(note);
    }

    @Test
    public void shouldFindNoteWithGivenValidId(){
        Note note = new Note();
        Mockito.when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));
        Note note2 = noteService.getNote(note.getId());
        Assertions.assertEquals(note,note2);
        verify(noteRepository).findById(note.getId());
    }

    @Test
    public void shouldThrowExceptionForNoteWithGivenInvalidId(){
        Note note = new Note();
        Mockito.when(noteRepository.findById(note.getId())).thenThrow(new NoteNotFoundException());
        Assertions.assertThrows(NoteNotFoundException.class, () -> noteService.getNote(note.getId()));
        verify(noteRepository).findById(note.getId());
    }

    @Test
    public void shouldFindListWithTwoNotes(){
        List<Note> list = new ArrayList<>();
        list.add(new Note());
        list.add(new Note());
        Mockito.when(noteRepository.findAll()).thenReturn(list);
        List<Note> allNotes = noteService.getAll();
        Assertions.assertEquals(2,allNotes.size());
    }

    @Test
    public void shouldFindEmptyListOfNotes(){
        List<Note> list = new ArrayList<>();
        Mockito.when(noteRepository.findAll()).thenReturn(list);
        List<Note> allNotes = noteService.getAll();
        Assertions.assertEquals(0,allNotes.size());
    }

    @Test
    public void withValidIdShouldDeleteNote(){
        Note note = new Note();
        Mockito.when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));
        noteService.deleteNote(note.getId());
        verify(noteRepository).deleteById(note.getId());
    }

    @Test
    public void shouldDeleteAllNotes(){
        noteService.deleteAll();
        verify(noteRepository).deleteAll();
    }

    @Test
    public void shouldUpdateNoteWithGivenId(){
        Note note = new Note();
        Note note2 = new Note();
        note2.setLikes(5);
        Mockito.when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));
        Mockito.when(noteRepository.save(note)).thenReturn(note2);
        Note updatedNote = noteService.updateNote(note.getId(), note);
        Assertions.assertEquals(5,updatedNote.getLikes());
    }
}