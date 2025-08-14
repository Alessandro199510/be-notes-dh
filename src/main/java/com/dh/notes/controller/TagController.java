package com.dh.notes.controller;

import com.dh.notes.dto.requests.TagRequest;
import com.dh.notes.dto.responses.PageResponse;
import com.dh.notes.dto.responses.TagResponse;
import com.dh.notes.service.TagService;
import com.dh.notes.util.Constans;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService TagService;

    public TagController(TagService TagService) {
        this.TagService = TagService;
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<TagResponse>> getAllTags(
            @RequestParam(defaultValue = Constans.DEFAULT_PAGE_INIT) int page,
            @RequestParam(defaultValue = Constans.DEFAULT_PAGE_SIZE) int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Constans.DEFAULT_SORT_BY).descending());
        PageResponse<TagResponse> response = TagService.getAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<TagResponse>> getTagsByUserAndQuery(
            @RequestParam(defaultValue = Constans.DEFAULT_PAGE_INIT) int page,
            @RequestParam(defaultValue = Constans.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(required = false) String search_query,
            Authentication authentication
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Constans.DEFAULT_SORT_BY).descending());
        PageResponse<TagResponse> response = TagService.findByUserAndQuery(pageable, search_query, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getTagById(@PathVariable Long id) {
        return TagService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TagResponse> createTag(
            @RequestBody TagRequest Tag,
            Authentication authentication
    ) {
        return ResponseEntity.ok(TagService.save(Tag, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> updateTag(@PathVariable Long id, @RequestBody TagRequest Tag) {
        return ResponseEntity.ok(TagService.update(id, Tag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        TagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}