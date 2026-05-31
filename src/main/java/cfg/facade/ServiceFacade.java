package cfg.facade;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.facade.AbstractFacade;

import cfg.model.Service;

@Stateless
@LocalBean
public class ServiceFacade extends AbstractFacade<Service> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceFacade() {
		super(Service.class);
	}
}
