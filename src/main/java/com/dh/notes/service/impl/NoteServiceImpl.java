package com.dh.notes.service.impl;

import com.dh.notes.dto.requests.NoteRequest;
import com.dh.notes.dto.responses.NoteResponse;
import com.dh.notes.model.Note;
import com.dh.notes.model.Tag;
import com.dh.notes.repository.NoteRepository;
import com.dh.notes.service.NoteService;
import com.dh.notes.util.Constans;
import com.dh.notes.util.TagsHelper;
import com.dh.notes.util.mappers.NoteToNoteResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final NoteToNoteResponse noteToNoteResponse;
    private final TagsHelper tagsHelper;

    public NoteServiceImpl(
            NoteRepository noteRepository,
            NoteToNoteResponse noteToNoteResponse,
            TagsHelper tagsHelper) {
        this.noteRepository = noteRepository;
        this.noteToNoteResponse = noteToNoteResponse;
        this.tagsHelper = tagsHelper;
    }



    @Override
    public Optional<NoteResponse> findById(Long id) {
        return noteRepository.findById(id)
                .map(noteToNoteResponse::map);
    }

    @Override
    public NoteResponse save(NoteRequest noteRequest) {

        if (noteRequest.getTitle() == null || noteRequest.getTitle().isEmpty()) {
            throw new RuntimeException(Constans.NOTE_TITLE_REQUIRED);
        }
        if (noteRequest.getContent() == null || noteRequest.getContent().isEmpty()) {
            throw new RuntimeException(Constans.NOTE_CONTENT_REQUIRED);
        }


        Note note = new Note();
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());

        Set<Tag> tags = this.tagsHelper.calTags(noteRequest.getTags());
        note.setTags(tags);

        Note savedNote = noteRepository.save(note);
        return noteToNoteResponse.map(savedNote);
    }

    @Override
    @Transactional
    public NoteResponse update(Long id, NoteRequest noteRequest) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Constans.NOTE_NOT_FOUND + id));

        existingNote.setTitle(noteRequest.getTitle());
        existingNote.setContent(noteRequest.getContent());
        existingNote.setStatus(noteRequest.getStatus());

        Set<Tag> tags = this.tagsHelper.calTags(noteRequest.getTags());
        existingNote.setTags(tags);

        Note updatedNote = noteRepository.save(existingNote);
        return noteToNoteResponse.map(updatedNote);
    }

    @Override
    public void delete(Long id) {
        noteRepository.deleteById(id);
    }
}