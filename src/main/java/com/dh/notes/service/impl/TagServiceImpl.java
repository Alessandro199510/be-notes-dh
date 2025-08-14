package com.dh.notes.service.impl;

import com.dh.notes.dto.requests.TagRequest;
import com.dh.notes.dto.responses.PageResponse;
import com.dh.notes.dto.responses.TagResponse;
import com.dh.notes.model.Tag;
import com.dh.notes.model.User;
import com.dh.notes.repository.TagRepository;
import com.dh.notes.repository.UserRepository;
import com.dh.notes.service.TagService;
import com.dh.notes.util.Constans;
import com.dh.notes.util.mappers.TagToTagResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final TagToTagResponse tagToTagResponse;

    public TagServiceImpl(
            TagRepository tagRepository,
            UserRepository userRepository,
            TagToTagResponse tagToTagResponse) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.tagToTagResponse = tagToTagResponse;
    }

    public PageResponse<TagResponse> getAll(Pageable pageable) {
        Page<Tag> tags = tagRepository.findAll(pageable);
        Page<TagResponse> tagsPage = tags.map(tagToTagResponse::map);
        return new PageResponse<>(tagsPage);
    }

    @Override
    public PageResponse<TagResponse> findByUserAndQuery(Pageable pageable, String searchQuery, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(Constans.USER_NOT_FOUND + username));

        Page<Tag> tags;
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            tags = tagRepository.findByUserAndQuery(searchQuery, user.getId(), pageable);
        } else {
            tags = tagRepository.findByUserId(user.getId(), pageable);
        }
        Page<TagResponse> tagsPage = tags.map(tagToTagResponse::map);

        return new PageResponse<>(tagsPage);

    }

    @Override
    public Optional<TagResponse> findById(Long id) {
        return tagRepository.findById(id)
                .map(tagToTagResponse::map);
    }

    @Override
    public TagResponse save(TagRequest noteRequest, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(Constans.USER_NOT_FOUND + username));

        Optional<Tag> existingTag = tagRepository.findByNameAndUserId(noteRequest.getName(), user.getId());

        if (existingTag.isPresent()) {
            throw new RuntimeException(Constans.TAG_ALREADY_EXISTS + noteRequest.getName());
        }

        Tag tag = new Tag();
        tag.setName(noteRequest.getName());
        tag.setUserId(user.getId());

        Tag savedTag = tagRepository.save(tag);
        return tagToTagResponse.map(savedTag);
    }

    @Override
    @Transactional
    public TagResponse update(Long id, TagRequest tagRequest) {
        Tag existingTag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Constans.NOTE_NOT_FOUND + id));

        existingTag.setName(tagRequest.getName());
        Tag updatedTag = tagRepository.save(existingTag);
        return tagToTagResponse.map(updatedTag);
    }

    @Override
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}