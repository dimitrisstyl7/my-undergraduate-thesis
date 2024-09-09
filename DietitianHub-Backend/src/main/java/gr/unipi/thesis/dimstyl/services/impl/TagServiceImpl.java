package gr.unipi.thesis.dimstyl.services.impl;

import gr.unipi.thesis.dimstyl.entities.Tag;
import gr.unipi.thesis.dimstyl.exceptions.tag.TagsMismatchException;
import gr.unipi.thesis.dimstyl.repositories.TagRepository;
import gr.unipi.thesis.dimstyl.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags(List<Integer> tagIds) {
        List<Tag> tags = tagRepository.findAllById(tagIds);

        if (tags.size() != tagIds.size()) {
            throw new TagsMismatchException(
                    "Tag list size mismatch (provided: %d, found: %d)".formatted(tagIds.size(), tags.size())
            );
        }

        return tags;
    }

}
