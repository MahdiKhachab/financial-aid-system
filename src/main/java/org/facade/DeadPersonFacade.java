package org.facade;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.model.DeadPerson;

@Stateless
@LocalBean
public class DeadPersonFacade extends AbstractFacade<DeadPerson> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeadPersonFacade() {
		super(DeadPerson.class);
	}
}
