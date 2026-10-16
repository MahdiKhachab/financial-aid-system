package cfg.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.facade.AbstractFacade;

import cfg.model.InssuranceCfg;

@Service
@Transactional
public class InssuranceCfgFacade extends AbstractFacade<InssuranceCfg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InssuranceCfgFacade() {
		super(InssuranceCfg.class);
	}
}
