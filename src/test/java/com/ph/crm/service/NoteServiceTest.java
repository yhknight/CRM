package com.ph.crm.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ph.crm.CrmApplicationForTest;
import com.ph.crm.domain.Note;
import com.ph.crm.exception.EntityNotExistException;
import com.ph.crm.repo.INoteRepo;

@RunWith(SpringJUnit4ClassRunner.class)
@Import(CrmApplicationForTest.class)
public class NoteServiceTest {

	@Autowired
	private INoteService noteService;

	@MockBean
	private INoteRepo noteRepo;

	@Test(expected = EntityNotExistException.class)
	public void updateNoteTestWithException() throws Exception {

		when(noteRepo.findById(anyLong())).thenReturn(Optional.empty());

		noteService.updateNote(1L, null);
	}

	@Test
	public void updateNoteTest() throws Exception {

		Note note1 = new Note();
		note1.setNote("note1");
		when(noteRepo.findById(anyLong())).thenReturn(Optional.of(note1));
		Note note2 = new Note();
		note2.setNote("note2");
		when(noteRepo.save(Mockito.any(Note.class))).thenReturn(note2);
		Assert.assertEquals("note2", noteService.updateNote(1L, note1).getNote());
	}

}
