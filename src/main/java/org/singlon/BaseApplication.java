package org.singlon;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.springframework.stereotype.Component;

import cfg.facade.SubmissionCfgFacade;

@Component
public class BaseApplication {

	
	@Inject
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
