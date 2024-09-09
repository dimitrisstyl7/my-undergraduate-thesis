package gr.unipi.thesis.dimstyl.repositories;

import gr.unipi.thesis.dimstyl.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
