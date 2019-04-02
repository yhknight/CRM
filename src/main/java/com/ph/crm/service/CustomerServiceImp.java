package com.ph.crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ph.crm.domain.Customer;
import com.ph.crm.domain.Note;
import com.ph.crm.exception.EntityNotExistException;
import com.ph.crm.repo.ICustomerRepo;
import com.ph.crm.repo.INoteRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImp implements ICustomerService {

	@Autowired
	private ICustomerRepo cusRepo;

	@Autowired
	private INoteRepo noteRepo;

	@Override
	public Customer createCustomer(Customer customer) {

		Optional<Customer> cusOpt = cusRepo.findByName(customer.getName());
		if (cusOpt.isPresent()) {
			throw new EntityNotExistException("Customer with the same name already exist");
		}
		log.debug("[createCustomer] customer name is {}", customer.getName());
		
		return cusRepo.save(customer);
	}

	@Override
	public Customer updateCustomer(Long id, Customer customer) {

		// find the target customer
		Optional<Customer> cusOpt = cusRepo.findById(id);

		// find the customer with same name
		Optional<Customer> cusOptNameCheck = cusRepo.findByName(customer.getName());

		Customer currentCus = cusOpt.orElseThrow(() -> new EntityNotExistException("customer doesn`t exist!"));

		cusOptNameCheck.ifPresent(item -> {
			if (!item.getId().equals(currentCus.getId())) {
				throw new RuntimeException("same customer already exist!");
			}
		});

		currentCus.setAddress(customer.getAddress());
		currentCus.setName(customer.getName());
		currentCus.setStatus(customer.getStatus());
		currentCus.setUpdatedBy(customer.getUpdatedBy());

		return cusRepo.save(currentCus);
	}

	@Override
	public List<Customer> retriverCustomer(String name) {

		return StringUtils.isEmpty(name) ? cusRepo.findAll() : cusRepo.findByNameContaining(name);
	}

	@Override
	public Note createNote(Long parentId, Note note) {
		Optional<Customer> cus = cusRepo.findById(parentId);
		if (cus.isPresent()) {
			note.setId(null);
			note.setCustomer(cus.get());
		} else {
			throw new EntityNotExistException("customer doesn`t exist!");
		}
		
		return noteRepo.save(note);
	}

}
