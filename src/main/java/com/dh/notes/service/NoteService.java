package com.dh.notes.service;

import com.dh.notes.dto.requests.NoteRequest;
import com.dh.notes.dto.responses.NoteResponse;
import com.dh.notes.dto.responses.PageResponse;
import com.dh.notes.util.enums.NoteStatus;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
public interface NoteService {
    PageResponse<NoteResponse> findByQueryAndStatusAndUser(Pageable pageable, String searchQuery, NoteStatus status, String username);
    PageResponse<NoteResponse> getAll(Pageable pageable);
    Optional<NoteResponse> findById(Long id);
    NoteResponse save(NoteRequest note, String username);
    NoteResponse update(Long id, NoteRequest note, String username);
    void delete(Long id);
}