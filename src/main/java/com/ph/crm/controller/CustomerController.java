package com.ph.crm.controller;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ph.crm.domain.Customer;
import com.ph.crm.domain.Note;
import com.ph.crm.dto.CustomerDTO;
import com.ph.crm.dto.NoteRequestDTO;
import com.ph.crm.dto.NoteResponseDTO;
import com.ph.crm.repo.ICustomerRepo;
import com.ph.crm.service.ICustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("customer")
@Slf4j
@Api(value = "customer resource")
public class CustomerController {

	@Autowired
	private ICustomerService customerService;

	@Autowired
	private ICustomerRepo customerRepo;

	@PostMapping()
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "create customer", notes = "unique customer name", httpMethod = "POST")
	@ApiImplicitParam(name = "customer", value = "customer", required = true, dataType = "CustomerDTO")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "create successfully"),
			@ApiResponse(code = 500, message = "server error") })
	public CustomerDTO createCustomer(@RequestBody CustomerDTO customer) {
		if (StringUtils.isEmpty(customer.getName())) {
			throw new InvalidParameterException("customer name can`t be blank.");
		}
		Customer request = new Customer();
		BeanUtils.copyProperties(customer, request);
		request = customerService.createCustomer(request);
		CustomerDTO response = new CustomerDTO();
		BeanUtils.copyProperties(request, response);
		return response;
	}

	@PutMapping(path = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	@ApiOperation(value = "update customer", notes = "unique customer name", httpMethod = "PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "customer id", required = true, dataType = "String", paramType = "path"),
			@ApiImplicitParam(name = "customer", value = "customer", required = true, dataType = "CustomerDTO",paramType="body")})
	@ApiResponses(value = { @ApiResponse(code = 201, message = "update successfully"),
			@ApiResponse(code = 500, message = "server error") })
	public CustomerDTO updateCsutomer(@PathVariable("id") String id, @RequestBody CustomerDTO customer) {
		log.debug("[updateCsutomer]customer id is {}", id);
		Customer request = new Customer();
		BeanUtils.copyProperties(customer, request);
		request = customerService.updateCustomer(Long.valueOf(id), request);
		CustomerDTO response = new CustomerDTO();
		BeanUtils.copyProperties(request, response);
		return response;
	}

	@GetMapping()
	@ApiOperation(value = "retreive customer", notes = "retrieve customer by name", httpMethod = "GET")
	@ApiImplicitParam(name = "name", value = "name", required = false, dataType = "String", paramType = "query")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "retrieve successfully"),
			@ApiResponse(code = 500, message = "server error") })
	public List<CustomerDTO> retrieveCustomer(@RequestParam(name = "name", required = false) String name) {
		log.debug("[retriveCustomer]name is {}", name);
		return customerService.retriverCustomer(name).stream().map(item -> {
			CustomerDTO entity = new CustomerDTO();
			BeanUtils.copyProperties(item, entity);
			return entity;
		}).collect(Collectors.toList());
	}

	@PostMapping(path = "/{id}/note")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "create note of customer", notes = "note of customer", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "customer id", required = true, dataType = "String", paramType = "path"),
			@ApiImplicitParam(name = "note", value = "note", required = false, dataType = "NoteRequestDTO",paramType="body")})
	@ApiResponses(value = { @ApiResponse(code = 201, message = "note create successfully"),
			@ApiResponse(code = 500, message = "server error") })
	public NoteResponseDTO createNote(@PathVariable("id") String id, @RequestBody NoteRequestDTO note) {

		Note noteEntity = new Note();
		BeanUtils.copyProperties(note, noteEntity);
		noteEntity = customerService.createNote(Long.valueOf(id), noteEntity);
		NoteResponseDTO response = new NoteResponseDTO();
		BeanUtils.copyProperties(noteEntity, response);
		response.setParentId(Long.valueOf(id));
		return response;
	}

	@GetMapping(path = "/{id}/note")
	@ApiOperation(value = "retreive customer`s notes", notes = "retrieve all notes of one customer", httpMethod = "GET")
	@ApiImplicitParam(name = "id", value = "customer id", required = true, dataType = "String", paramType = "path")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "note retreve successfully"),
			@ApiResponse(code = 500, message = "server error") })
	public List<NoteResponseDTO> getAllNoteByCustomer(@PathVariable("id") String id) {
		Optional<Customer> customer = customerRepo.findById(Long.valueOf(id));

		if (customer.isPresent()) {

			return customer.get().getNotes().stream().map(item -> {
				NoteResponseDTO entity = new NoteResponseDTO();
				BeanUtils.copyProperties(item, entity);
				entity.setParentId(Long.valueOf(id));
				return entity;
			}).collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}

}
