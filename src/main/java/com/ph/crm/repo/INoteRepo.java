package com.ph.crm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ph.crm.domain.Note;

@Repository
public interface INoteRepo extends JpaRepository<Note, Long> {

}
