package cfg.facade;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import org.facade.AbstractFacade;

import cfg.model.Service;

@org.springframework.stereotype.Service
@Transactional
public class ServiceFacade extends AbstractFacade<Service> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceFacade() {
		super(Service.class);
	}
}
