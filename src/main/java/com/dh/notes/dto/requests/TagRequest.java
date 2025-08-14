package com.dh.notes.dto.requests;

import com.dh.notes.util.enums.NoteStatus;
import lombok.Data;

import java.util.Set;

@Data
public class TagRequest {
    private String name;
}
