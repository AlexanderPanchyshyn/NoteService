package com.pb.test.controller;

import com.pb.test.model.entity.Note;
import com.pb.test.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    NoteService noteService;

    @ApiIgnore
    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getAllNotes() {
        return noteService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Note getNoteById(@PathVariable("id") String id) {
        return noteService.getNote(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Note saveNote(@RequestBody Note note) {
        return noteService.saveNote(note);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Note updateNoteById(@PathVariable("id") String id, @RequestBody Note note){
        return noteService.updateNote(id, note);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public void deleteAll() {
        noteService.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNoteById(@PathVariable("id") String id) {
        noteService.deleteNote(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/increase_like")
    @ResponseStatus(HttpStatus.OK)
    public Note increaseLikesInNote(@PathVariable("id") String id){
        return noteService.increaseLikesInNote(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/decrease_like")
    @ResponseStatus(HttpStatus.OK)
    public Note decreaseLikesInNote(@PathVariable("id") String id){
        return noteService.decreaseLikesInNote(id);
    }

}
