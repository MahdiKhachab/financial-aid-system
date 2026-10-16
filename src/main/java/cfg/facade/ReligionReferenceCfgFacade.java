package cfg.facade;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.facade.AbstractFacade;

import cfg.model.ReligionReferenceCfg;

@Service
@Transactional
public class ReligionReferenceCfgFacade extends AbstractFacade<ReligionReferenceCfg> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReligionReferenceCfgFacade() {
		super(ReligionReferenceCfg.class);
	}
}
