package com.pm.fsnotes.repository;

import com.pm.fsnotes.entity.note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NoteRepository extends JpaRepository<note, Integer> {

}
