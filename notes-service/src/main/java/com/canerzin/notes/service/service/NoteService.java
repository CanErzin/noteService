package com.canerzin.notes.service.service;

import com.canerzin.notes.service.dto.NoteDto;
import com.canerzin.notes.service.dto.NoteLikeAction;
import com.canerzin.notes.service.model.Note;
import com.canerzin.notes.service.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getAllNotes() {
        return noteRepository.findAll(Sort.by(Sort.Direction.DESC, "createDateTime"));
    }

    public Note createNote(NoteDto noteDto) {
        Note note = new Note();
        note.setContent(noteDto.getContent());
        note.setCreateDateTime(LocalDateTime.now());
        note.setLike(0);
        return noteRepository.save(note);
    }

    public Note updateNoteLike(String id, NoteLikeAction action) throws Exception {
        var optional = noteRepository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Note not found");
        }
        synchronized (this){
            var note = optional.get();
            var likes = note.getLike();
            if (action.equals(NoteLikeAction.LIKE)) note.setLike(++likes);
            else {
                note.setLike(--likes);
            }
            return noteRepository.save(note);
        }
    }



}
