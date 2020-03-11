package com.javacodegeeks.examples.jpa.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javacodegeeks.examples.jpa.model.Country;
import com.javacodegeeks.examples.jpa.model.State;
import com.javacodegeeks.examples.jpa.model.UsersContext;
import com.javacodegeeks.examples.jpa.repository.BaseDAOInt;
import com.javacodegeeks.examples.jpa.repository.CountryDAOInt;
import com.javacodegeeks.examples.jpa.repository.StateDAOInt;

@Service
public class StateServiceImpl<T extends State, D extends BaseDAOInt<State>> extends BaseServiceImpl<State>
		implements StateServiceInt<State> {

	private static final Logger LOGGER = LogManager.getLogger(NotificationServiceImpl.class);

	@Autowired
	CountryDAOInt countryRepository;

	@Autowired
	StateDAOInt stateRepository;

	@Transactional
	@Override
	public State save(State dto, UsersContext userContext) {
		try {
			if (dto != null) {
				Country country = new Country();
				long id = dto.getCountryId();
				if (id > 0) {
					country = countryRepository.findById(id).get();
					if (country != null) {
						dto.setCountry(country.getCountryName());
						return stateRepository.save(dto);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());

		}
		return null;
	}

}
