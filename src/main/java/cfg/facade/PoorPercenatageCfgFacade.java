package cfg.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.facade.AbstractFacade;

import cfg.model.PoorPercentageCfg;

@Service
@Transactional
public class PoorPercenatageCfgFacade extends AbstractFacade<PoorPercentageCfg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PoorPercenatageCfgFacade() {
		super(PoorPercentageCfg.class);
	}

}
