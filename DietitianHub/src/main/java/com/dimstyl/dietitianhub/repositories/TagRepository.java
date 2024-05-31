package com.dimstyl.dietitianhub.repositories;

import com.dimstyl.dietitianhub.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
