package com.ph.crm.controller;

import java.security.InvalidParameterException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ph.crm.domain.Note;
import com.ph.crm.dto.NoteRequestDTO;
import com.ph.crm.dto.NoteResponseDTO;
import com.ph.crm.service.INoteService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("note")
public class NoteController {

	@Autowired
	private INoteService noteService;

	@PutMapping("/{id}")
	@ApiOperation(value = "update note", notes = "note can not be empry", httpMethod = "PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "note id", required = true, dataType = "String", paramType = "path"),
			@ApiImplicitParam(name = "note", value = "note", required = true, dataType = "NoteRequestDTO", paramType = "body") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "note update successfully"),
			@ApiResponse(code = 500, message = "server error") })
	public NoteResponseDTO updateNote(@PathVariable("id") String id, @RequestBody NoteRequestDTO note) {

		if (StringUtils.isEmpty(note.getNote())) {
			throw new InvalidParameterException("note can`t be blank.");
		}
		Note entity = new Note();
		entity.setNote(note.getNote());
		entity.setUpdatedBy(note.getUpdatedBy());

		NoteResponseDTO response = new NoteResponseDTO();
		entity = noteService.updateNote(Long.valueOf(id), entity);
		BeanUtils.copyProperties(entity, response);
		response.setParentId(entity.getCustomer().getId());
		response.setId(Long.valueOf(id));
		return response;
	}

}
