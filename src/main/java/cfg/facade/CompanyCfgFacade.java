package cfg.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.facade.AbstractFacade;

import cfg.model.CompanyCfg;

@Stateless
@LocalBean
public class CompanyCfgFacade extends AbstractFacade<CompanyCfg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CompanyCfgFacade() {
		super(CompanyCfg.class);
	}
}
