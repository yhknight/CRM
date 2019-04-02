package com.ph.crm.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ph.crm.domain.Customer;

@Repository
public interface ICustomerRepo extends JpaRepository<Customer, Long> {

	
	Optional<Customer> findByName(String name);
	
	//@Query("select from Customer JOIN Note")
	List<Customer> findByNameContaining(String name);

}
