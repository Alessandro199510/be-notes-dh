package com.dh.notes.dto.responses;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class NoteResponse {
    private Long id;
    private String title;
    private String content;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<String> tags;
}
