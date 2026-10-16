package cfg.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.facade.AbstractFacade;

import cfg.model.HealthyStatusCfg;

@Service
@Transactional
public class HealthyStatusCfgFacade extends AbstractFacade<HealthyStatusCfg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HealthyStatusCfgFacade() {
		super(HealthyStatusCfg.class);
	}
}
