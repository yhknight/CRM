package com.ph.crm.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Entity
@Data
public class Note extends BaseDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String note;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Customer customer;

	@PrePersist
	public void prePost() {
		this.setCreateTime(new Date());
	}

	@PreUpdate
	public void preUpdate() {
		this.setUpdateTime(new Date());

	}
}
