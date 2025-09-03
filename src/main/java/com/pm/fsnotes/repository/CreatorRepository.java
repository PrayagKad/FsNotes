package com.pm.fsnotes.repository;

import com.pm.fsnotes.entity.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, Long> {
    Creator findByUsername(String username);
}
