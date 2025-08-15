package com.dh.notes.service;

import com.dh.notes.dto.requests.NoteRequest;
import com.dh.notes.dto.responses.NoteResponse;
import com.dh.notes.dto.responses.PageResponse;
import com.dh.notes.util.enums.NoteStatus;
import org.springframework.data.domain.Pageable;

public interface NoteService {
    PageResponse<NoteResponse> findNotes(
            Pageable pageable,
            String searchQuery,
            NoteStatus status,
            Long tagId,
            String username
    );
    PageResponse<NoteResponse> getAll(Pageable pageable);
    NoteResponse findById(Long id);
    NoteResponse save(NoteRequest note, String username);
    NoteResponse update(Long id, NoteRequest note, String username);
    void delete(Long id);
}