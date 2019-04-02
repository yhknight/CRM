package com.ph.crm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ph.crm.domain.Note;
import com.ph.crm.exception.EntityNotExistException;
import com.ph.crm.repo.INoteRepo;

@Service
public class NoteServiceImp implements INoteService {
	@Autowired
	private INoteRepo noteRepo;

	@Override
	public Note updateNote(Long id, Note note) {
		Optional<Note> noteOpt = noteRepo.findById(id);
		Note entity = null;
		if (noteOpt.isPresent()) {
			entity = noteOpt.get();
			entity.setNote(note.getNote());
			entity.setUpdatedBy(note.getUpdatedBy());
		} else {
			throw new EntityNotExistException("Note doesn`t exist.");
		}
		return noteRepo.save(entity);
	}

}
