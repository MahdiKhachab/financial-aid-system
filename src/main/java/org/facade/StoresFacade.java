package org.facade;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.model.Stores;

@Stateless
@LocalBean
public class StoresFacade extends AbstractFacade<Stores> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StoresFacade() {
		super(Stores.class);
	}

}
