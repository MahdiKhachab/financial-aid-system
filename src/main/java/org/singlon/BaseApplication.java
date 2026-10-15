package org.singlon;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import cfg.facade.SubmissionCfgFacade;

@Singleton
@Startup
public class BaseApplication {

	
	@EJB
	private SubmissionCfgFacade submissionCfgFacade;
	
	
	// String for t2dim kase2m shr2ye 
	private static final String  KAS2EM_SHIRA2YE_NAME = "قسائم شرائية "; 
	
	@PostConstruct
	public void init() {
		insertIfNotExistKasseem();
		
		
	}
	
	public void insertIfNotExistKasseem() {
		try {
			submissionCfgFacade.insertfSubmissionCfgIfNotExist(KAS2EM_SHIRA2YE_NAME);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
