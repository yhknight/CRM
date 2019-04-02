package com.ph.crm.dto;

import java.util.Date;

import lombok.Data;

@Data
public class NoteResponseDTO {

	private Long id;

	private String note;

	private String createdBy;

	private String updatedBy;

	private Long parentId;

	private Date CreateTime;

	private Date UpdateTime;
}
