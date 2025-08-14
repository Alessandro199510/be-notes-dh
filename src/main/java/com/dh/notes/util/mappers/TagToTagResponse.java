package com.dh.notes.util.mappers;

import com.dh.notes.dto.responses.TagResponse;
import com.dh.notes.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagToTagResponse {
    public TagResponse map(Tag tag) {
        TagResponse response = new TagResponse();
        response.setId(tag.getId());
        response.setName(tag.getName());
        return response;
    }
}
