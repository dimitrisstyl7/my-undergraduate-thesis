package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.entities.Tag;

import java.util.List;

public interface TagService {

    List<Tag> getTags(List<Integer> tagIds);

}
