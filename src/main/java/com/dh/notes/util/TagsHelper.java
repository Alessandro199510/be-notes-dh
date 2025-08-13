package com.dh.notes.util;

import com.dh.notes.model.Tag;
import com.dh.notes.repository.TagRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class TagsHelper {

    private final TagRepository tagRepository;

    public TagsHelper(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Set<Tag> calTags(Set<String> tagsRequest) {
        Set<Tag> tags = new HashSet<>();

        if (tagsRequest != null) {
            for (String tagName : tagsRequest) {
                Optional<Tag> existingTag = tagRepository.findByName(tagName);
                Tag tag;
                if (existingTag.isPresent()) {
                    tag = existingTag.get();
                    tags.add(tag);
                } else {
                    tag = new Tag();
                    tag.setName(tagName);
                    tagRepository.save(tag);
                    tags.add(tag);
                }
            }
        }

        return tags;
    }
}
