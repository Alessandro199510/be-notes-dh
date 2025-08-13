package com.dh.notes.dto.requests;

import com.dh.notes.util.enums.NoteStatus;
import lombok.Data;

import java.util.Set;

@Data
public class NoteRequest {
    private String title;
    private String content;
    private Set<String> tags;
    private NoteStatus status;
}
