package com.canerzin.notes.service.controller;

import com.canerzin.notes.service.dto.NoteDto;
import com.canerzin.notes.service.dto.NoteLikeAction;
import com.canerzin.notes.service.model.Note;
import com.canerzin.notes.service.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getAllNotes(){
        return new ResponseEntity<>(noteService.getAllNotes(), HttpStatus.OK);
    }

    @PostMapping("/notes")
    public ResponseEntity<Note> createNote(@RequestBody NoteDto noteDto){
        return new ResponseEntity<>(noteService.createNote(noteDto),HttpStatus.CREATED);
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<Note> updateLike(@PathVariable("id") String id, NoteLikeAction action) throws Exception {
        return new ResponseEntity<>(noteService.updateNoteLike(id,action),HttpStatus.OK);
    }
}
