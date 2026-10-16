package cfg.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.facade.AbstractFacade;

import cfg.model.CompanyCfg;

@Service
@Transactional
public class CompanyCfgFacade extends AbstractFacade<CompanyCfg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CompanyCfgFacade() {
		super(CompanyCfg.class);
	}
}
