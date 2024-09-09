package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.entities.Tag;

import java.util.List;

public interface TagService {

    List<Tag> getTags(List<Integer> tagIds);

}
