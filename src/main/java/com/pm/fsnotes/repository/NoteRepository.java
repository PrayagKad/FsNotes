package com.pm.fsnotes.repository;

import com.pm.fsnotes.entity.Creator;
import com.pm.fsnotes.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {


    /*
     * Find all notes by a specific creator (user).
     *
     * @param creator the user who created the notes
     * @return list of notes for that creator
     */
    List<Note> findByCreator(Creator creator);

    /*
     * Find a note by its sharable link (UUID).
     * @param sharablelink unique sharable link
     * @return Optional note
     */
    Optional<Note> findBySharablelink(String sharablelink);
}
