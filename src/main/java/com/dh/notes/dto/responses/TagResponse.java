package com.dh.notes.dto.responses;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TagResponse {
    private Long id;
    private String name;
}