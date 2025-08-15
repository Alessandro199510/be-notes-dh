package com.dh.notes.util;

import com.dh.notes.model.Tag;
import com.dh.notes.model.User;
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

    public Set<Tag> calcTags(Set<String> tagsRequest, User user) {
        Set<Tag> tags = new HashSet<>();

        if (tagsRequest != null) {
            for (String tagName : tagsRequest) {
                Optional<Tag> existingTag = tagRepository.findByNameAndUserId(tagName, user.getId());
                Tag tag;
                if (existingTag.isPresent()) {
                    tag = existingTag.get();
                    tags.add(tag);
                } else {
                    tag = new Tag();
                    tag.setName(tagName);
                    tag.setUserId(user.getId());
                    tagRepository.save(tag);
                    tags.add(tag);
                }
            }
        }

        return tags;
    }
}
