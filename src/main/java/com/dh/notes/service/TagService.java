package com.dh.notes.service;

import com.dh.notes.dto.requests.TagRequest;
import com.dh.notes.dto.responses.PageResponse;
import com.dh.notes.dto.responses.TagResponse;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TagService {
    PageResponse<TagResponse> findByUserAndQuery(Pageable pageable, String searchQuery, String username);
    PageResponse<TagResponse> getAll(Pageable pageable);
    Optional<TagResponse> findById(Long id);
    TagResponse save(TagRequest note, String username);
    TagResponse update(Long id, TagRequest note);
    void delete(Long id);
}