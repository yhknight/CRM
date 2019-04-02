package com.ph.crm.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Entity
@Data
public class Customer extends BaseDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Enumerated(EnumType.STRING)
	private Status status;

	private String address;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer",fetch=FetchType.LAZY)
	private List<Note> notes = new ArrayList<>();

	@PrePersist
	public void prePost() {
		this.setCreateTime(new Date());
	}

	@PreUpdate
	public void preUpdate() {
		this.setUpdateTime(new Date());

	}

}
