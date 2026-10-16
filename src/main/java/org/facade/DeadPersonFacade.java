package org.facade;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.model.DeadPerson;

@Service
@Transactional
public class DeadPersonFacade extends AbstractFacade<DeadPerson> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeadPersonFacade() {
		super(DeadPerson.class);
	}
}
