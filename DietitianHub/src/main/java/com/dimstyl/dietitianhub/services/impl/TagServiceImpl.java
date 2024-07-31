package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.entities.Tag;
import com.dimstyl.dietitianhub.exceptions.TagsMismatchException;
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
            throw new TagsMismatchException(
                    "Tag list size mismatch (provided: %d, found: %d)".formatted(tagIds.size(), tags.size())
            );
        }

        return tags;
    }

}
