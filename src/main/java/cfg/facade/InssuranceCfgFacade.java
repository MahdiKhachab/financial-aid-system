package cfg.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.facade.AbstractFacade;

import cfg.model.InssuranceCfg;

@Stateless
@LocalBean
public class InssuranceCfgFacade extends AbstractFacade<InssuranceCfg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InssuranceCfgFacade() {
		super(InssuranceCfg.class);
	}
}
