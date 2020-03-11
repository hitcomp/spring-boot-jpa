package com.javacodegeeks.examples.jpa.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.javacodegeeks.examples.jpa.SpringBootJpaApplication;
import com.javacodegeeks.examples.jpa.model.BaseModel;
import com.javacodegeeks.examples.jpa.model.Response;
import com.javacodegeeks.examples.jpa.model.Users;
import com.javacodegeeks.examples.jpa.model.UsersContext;
import com.javacodegeeks.examples.jpa.service.BaseServiceInt;

@CrossOrigin(origins = "*")
public class BaseCtl<T extends BaseModel, S extends BaseServiceInt<T>> {

	private static final Logger LOGGER = LogManager.getLogger(SpringBootJpaApplication.class);

	@Autowired
	private S service;

	Response responsese = null;

	protected UsersContext userContext = null;

	@ModelAttribute
	public void setUserContext(HttpSession session) {
		userContext = (UsersContext) session.getAttribute("userContext");
		if (userContext == null) {
			Users dto = new Users();
			dto.setEmail("farhana.khan@hiteshi.com");
			dto.setFirstName("Dummy name");
			dto.setLastName("Dummy name");
			userContext = new UsersContext(dto);
		}
	}

	// Method For Find by Id
	@GetMapping(value = "/findById/{id}")
	public Response findById(@PathVariable long id) throws Exception {
		responsese = new Response<T>();

		T dto = service.findByPk(id, userContext);

		if (dto != null) {
			responsese.setMessage(HttpStatus.FOUND);
			responsese.setObject(dto);
			responsese.setStatus(true);
		} else {
			responsese.setMessage(HttpStatus.NOT_FOUND);
			responsese.setStatus(false);
		}

		return responsese;
	}

	// Method For Save

	@PostMapping(value = "/save")
	public Response save(@RequestBody T baseDto) throws Exception {
		responsese = new Response<T>();
		if (service.save(baseDto, userContext) != null) {
			responsese.setMessage(HttpStatus.ACCEPTED);
			responsese.setStatus(true);
		} else {
			responsese.setMessage(HttpStatus.NOT_ACCEPTABLE);
			responsese.setStatus(false);
		}
		return responsese;

	}

	// Method For Delete User

	@DeleteMapping(value = "/delete")
	public Response delete(@RequestParam long id) throws Exception {
		responsese = new Response<T>();
		if (id > 0) {
			service.delete(id, userContext);
			responsese.setMessage(HttpStatus.ACCEPTED);
			responsese.setStatus(true);
		} else {
			responsese.setMessage(HttpStatus.NOT_ACCEPTABLE);
			responsese.setStatus(false);
		}
		return responsese;

	}
	// Method For Search

	@PostMapping("/search")
	public Response search(@RequestBody T baseDto) throws Exception {
		responsese = new Response<T>();
		List list = (List) service.search(baseDto, userContext);
		if (list != null && list.size() > 0) {
			responsese.setMessage(HttpStatus.FOUND);
			responsese.setList(list);
			responsese.setRecordCount(service.recordCount());
			responsese.setStatus(true);
		} else {
			responsese.setMessage(HttpStatus.NOT_FOUND);
			responsese.setStatus(false);
		}
		return responsese;

	}
	
	@PostMapping("/getAll")
	public Response getAll() throws Exception {
		responsese = new Response<T>();
		List list = (List) service.findAll();
		if (list != null && list.size() > 0) {
			responsese.setMessage(HttpStatus.FOUND);
			responsese.setList(list);
			responsese.setStatus(true);
		} else {
			responsese.setMessage(HttpStatus.NOT_FOUND);
			responsese.setStatus(false);
		}
		return responsese;

	}

	// Method For Status

	@PostMapping("/statusActive")
	public Response status(@RequestParam long id, @RequestParam boolean status, @RequestParam String type) {
		responsese = new Response<T>();
		if (type != null && type != null)
			if (service.Status(id, status, type)) {
				responsese.setMessage(HttpStatus.ACCEPTED);
				responsese.setStatus(true);
			} else {
				responsese.setMessage(HttpStatus.NOT_FOUND);
				responsese.setStatus(false);
			}

		return responsese;
	}

}