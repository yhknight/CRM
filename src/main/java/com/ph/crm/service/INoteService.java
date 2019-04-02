package com.ph.crm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ph.crm.domain.Note;

@Service
public interface INoteService {
	@Transactional
	Note updateNote(Long id, Note note);
}
