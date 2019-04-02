package com.ph.crm.dto;

import java.util.Date;

import com.ph.crm.domain.Status;

import lombok.Data;

@Data
public class CustomerDTO {
	private Long id;

	private String name;

	private Status status;

	private String address;

	private String CreatedBy;

	private Date CreateTime;
	
	private String UpdatedBy;

	private Date UpdateTime;
}
