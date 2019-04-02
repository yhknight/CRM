package com.ph.crm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ph.crm.domain.Customer;
import com.ph.crm.domain.Note;

@Service
public interface ICustomerService {

	@Transactional
	Customer createCustomer(Customer customer);

	@Transactional
	Customer updateCustomer(Long id, Customer customer);

	List<Customer> retriverCustomer(String name);

	@Transactional
	Note createNote(Long parentId, Note note);

}
