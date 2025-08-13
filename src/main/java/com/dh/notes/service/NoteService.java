package com.dh.notes.service;

import com.dh.notes.dto.requests.NoteRequest;
import com.dh.notes.dto.responses.NoteResponse;

import java.util.Optional;

public interface NoteService {
    Optional<NoteResponse> findById(Long id);
    NoteResponse save(NoteRequest note);
    NoteResponse update(Long id, NoteRequest note);
    void delete(Long id);
}