package com.dh.notes.controller;

import com.dh.notes.dto.requests.NoteRequest;
import com.dh.notes.dto.responses.NoteResponse;
import com.dh.notes.dto.responses.PageResponse;
import com.dh.notes.util.Constans;
import com.dh.notes.service.NoteService;
import com.dh.notes.util.enums.NoteStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")

public class NoteController {

    private final NoteService noteService;

    public NoteController(
            NoteService noteService
    ) {
        this.noteService = noteService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<PageResponse<NoteResponse>> getAllNotes(
            @RequestParam(defaultValue = Constans.DEFAULT_PAGE_INIT) int page,
            @RequestParam(defaultValue = Constans.DEFAULT_PAGE_SIZE) int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Constans.DEFAULT_SORT_BY).descending());
        PageResponse<NoteResponse> response = noteService.getAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<NoteResponse>> findNotes(
            @RequestParam(defaultValue = Constans.DEFAULT_PAGE_INIT) int page,
            @RequestParam(defaultValue = Constans.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(required = false) String search_query,
            @RequestParam(defaultValue = "ACTIVE") NoteStatus status,
            Authentication authentication
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Constans.DEFAULT_SORT_BY).descending());
        PageResponse<NoteResponse> response = noteService.findByQueryAndStatusAndUser(pageable, search_query, status, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(
            @RequestBody NoteRequest note,
            Authentication authentication
    ) {
        return ResponseEntity.ok(noteService.save(note, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable Long id,
            @RequestBody NoteRequest note,
            Authentication authentication
    ) {
        return ResponseEntity.ok(noteService.update(id, note, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}