package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.entities.Tag;
import com.dimstyl.dietitianhub.repositories.TagRepository;
import com.dimstyl.dietitianhub.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTagsByIds(List<Integer> tagIds) {
        List<Tag> tags = tagRepository.findAllById(tagIds);

        if (tags.isEmpty() || tags.size() != tagIds.size()) {
            // Todo: Throw a custom exception and handle it in the custom exception handler.
        }

        return tags;
    }
}
