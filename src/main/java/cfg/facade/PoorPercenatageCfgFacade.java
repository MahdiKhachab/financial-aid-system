package cfg.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.facade.AbstractFacade;

import cfg.model.PoorPercentageCfg;

@Stateless
@LocalBean
public class PoorPercenatageCfgFacade extends AbstractFacade<PoorPercentageCfg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PoorPercenatageCfgFacade() {
		super(PoorPercentageCfg.class);
	}

}
