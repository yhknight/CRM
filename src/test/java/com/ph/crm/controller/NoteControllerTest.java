package com.ph.crm.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ph.crm.CrmApplicationForTest;
import com.ph.crm.domain.Customer;
import com.ph.crm.domain.Note;
import com.ph.crm.dto.NoteRequestDTO;
import com.ph.crm.dto.NoteResponseDTO;
import com.ph.crm.service.INoteService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = { NoteController.class })
@ActiveProfiles("test")
public class NoteControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	private INoteService noteService;

	@Test
	public void updateNoteTest() throws Exception {

		ObjectMapper om = new ObjectMapper();

		NoteRequestDTO requestBody = new NoteRequestDTO();
		requestBody.setNote("test");
		requestBody.setUpdatedBy("Tom");

		Note note = new Note();
		note.setId(1L);
		note.setUpdatedBy("Tom");
		note.setNote("test");
		note.setCustomer(new Customer() {
			{
				setId(2L);
			}
		});

		NoteResponseDTO responseDTO = new NoteResponseDTO();

		BeanUtils.copyProperties(note, responseDTO);

		responseDTO.setParentId(note.getCustomer().getId());
		responseDTO.setId(note.getId());

		Mockito.when(noteService.updateNote(Mockito.anyLong(), Mockito.any(Note.class))).thenReturn(note);

		mvc.perform(MockMvcRequestBuilders.put("/note/{id}", "1").content(om.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(om.writeValueAsString(responseDTO)));
	}

}
