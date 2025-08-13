package com.dh.notes.util.mappers;

import com.dh.notes.dto.responses.NoteResponse;
import com.dh.notes.model.Note;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class NoteToNoteResponse {
    public NoteResponse map(Note note) {
        NoteResponse response = new NoteResponse();
        response.setId(note.getId());
        response.setTitle(note.getTitle());
        response.setContent(note.getContent());
        response.setStatus(note.getStatus().toString());
        response.setCreatedAt(note.getCreatedAt());
        response.setUpdatedAt(note.getUpdatedAt());

        Set<String> tags = new HashSet<>();
        if (note.getTags() != null) {
            note.getTags().forEach(tag -> tags.add(tag.getName()));
        }
        response.setTags(tags);

        return response;
    }
}
