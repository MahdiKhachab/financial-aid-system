package org.facade;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.model.Sponsor;

@Service
@Transactional
public class SponsorFacade extends AbstractFacade<Sponsor> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SponsorFacade() {
		super(Sponsor.class);
	}

}



