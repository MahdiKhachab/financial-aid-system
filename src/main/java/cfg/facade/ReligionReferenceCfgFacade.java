package cfg.facade;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.facade.AbstractFacade;

import cfg.model.ReligionReferenceCfg;

@Stateless
@LocalBean
public class ReligionReferenceCfgFacade extends AbstractFacade<ReligionReferenceCfg> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReligionReferenceCfgFacade() {
		super(ReligionReferenceCfg.class);
	}
}
