package com.dh.notes.service.impl;

import com.dh.notes.dto.requests.NoteRequest;
import com.dh.notes.dto.responses.NoteResponse;
import com.dh.notes.dto.responses.PageResponse;
import com.dh.notes.exception.NoteNotFoundException;
import com.dh.notes.exception.NoteValidationException;
import com.dh.notes.exception.UserNotFoundException;
import com.dh.notes.model.Note;
import com.dh.notes.model.Tag;
import com.dh.notes.model.User;
import com.dh.notes.repository.NoteRepository;
import com.dh.notes.repository.TagRepository;
import com.dh.notes.repository.UserRepository;
import com.dh.notes.service.NoteService;
import com.dh.notes.util.Constans;
import com.dh.notes.util.TagsHelper;
import com.dh.notes.util.enums.NoteStatus;
import com.dh.notes.util.mappers.NoteToNoteResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    private final NoteToNoteResponse noteToNoteResponse;
    private final TagsHelper tagsHelper;

    public NoteServiceImpl(
            NoteRepository noteRepository,
            UserRepository userRepository,
            TagRepository tagRepository,
            NoteToNoteResponse noteToNoteResponse,
            TagsHelper tagsHelper) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.noteToNoteResponse = noteToNoteResponse;
        this.tagsHelper = tagsHelper;
    }

    @Override
    public PageResponse<NoteResponse> getAll(Pageable pageable) {
        Page<Note> notes = noteRepository.findAll(pageable);
        Page<NoteResponse> notesPage = notes.map(noteToNoteResponse::map);
        return new PageResponse<>(notesPage);
    }

    @Override
    public PageResponse<NoteResponse> findNotes(
            Pageable pageable,
            String searchQuery,
            NoteStatus status,
            Long tagId,
            String username
    ) {

        Page<Note> notes = null;

        if (tagId != null) {
            tagRepository.findById(tagId)
                    .orElseThrow(() -> new NoteNotFoundException(Constans.TAG_NOT_FOUND + tagId));

            notes = noteRepository.searchNotesByTag(searchQuery.trim(), status, username, tagId, pageable);
        }

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            notes = noteRepository.searchNotes(searchQuery.trim(), status, username, pageable);
        }

        if (tagId == null && searchQuery == null) {
            notes = noteRepository.findAllByStatus(status, username, pageable);
        }

        Page<NoteResponse> notesPage = notes.map(noteToNoteResponse::map);

        return new PageResponse<>(notesPage);
    }

    @Override
    public NoteResponse findById(Long id) {
        return noteRepository.findById(id)
                .map(noteToNoteResponse::map)
                .orElseThrow(() -> new NoteNotFoundException(Constans.NOTE_NOT_FOUND));
    }

    @Override
    public NoteResponse save(NoteRequest noteRequest, String username) {

        if (noteRequest.getTitle() == null || noteRequest.getTitle().isEmpty()) {
            throw new NoteValidationException(Constans.NOTE_TITLE_REQUIRED);
        }
        if (noteRequest.getContent() == null || noteRequest.getContent().isEmpty()) {
            throw new NoteValidationException(Constans.NOTE_CONTENT_REQUIRED);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(Constans.USER_NOT_FOUND + username));

        Note note = new Note();
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        note.setStatus(noteRequest.getStatus());
        note.setUser(user);

        Set<Tag> tags = this.tagsHelper.calcTags(noteRequest.getTags(), user);
        note.setTags(tags);

        Note savedNote = noteRepository.save(note);
        return noteToNoteResponse.map(savedNote);
    }

    @Override
    @Transactional
    public NoteResponse update(Long id, NoteRequest noteRequest, String username) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(Constans.NOTE_NOT_FOUND + id));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(Constans.USER_NOT_FOUND + username));

        existingNote.setTitle(noteRequest.getTitle());
        existingNote.setContent(noteRequest.getContent());
        existingNote.setStatus(noteRequest.getStatus());

        Set<Tag> tags = this.tagsHelper.calcTags(noteRequest.getTags(), user);
        existingNote.setTags(tags);

        Note updatedNote = noteRepository.save(existingNote);
        return noteToNoteResponse.map(updatedNote);
    }

    @Override
    public void delete(Long id) {
        noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(Constans.NOTE_NOT_FOUND + id));
        noteRepository.deleteById(id);
    }
}