package org.facade;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.model.Deserved;
import org.model.Sponsor;

@Stateless
@LocalBean
public class SponsorFacade extends AbstractFacade<Sponsor> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SponsorFacade() {
		super(Sponsor.class);
	}

}



