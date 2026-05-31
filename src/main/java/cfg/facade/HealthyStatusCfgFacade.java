package cfg.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.facade.AbstractFacade;

import cfg.model.HealthyStatusCfg;

@Stateless
@LocalBean
public class HealthyStatusCfgFacade extends AbstractFacade<HealthyStatusCfg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HealthyStatusCfgFacade() {
		super(HealthyStatusCfg.class);
	}
}
