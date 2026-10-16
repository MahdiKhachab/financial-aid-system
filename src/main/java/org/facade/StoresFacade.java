package org.facade;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.model.Stores;

@Service
@Transactional
public class StoresFacade extends AbstractFacade<Stores> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StoresFacade() {
		super(Stores.class);
	}

}
