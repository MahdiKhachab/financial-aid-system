package org.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import cfg.facade.SubmissionCfgFacade;
import cfg.model.SubmissionCfg;
import cfg.model.SubmissionDetailsCfg;

@Stateless
@LocalBean
public class ExternalRepportFacade {

	@EJB
	private SubmissionCfgFacade submissionCfgFacade;

	public List<SubmissionDetailsCfg> getAllSubmissionDetailsCfg() {
		List<SubmissionCfg> listSubmissionCfg = submissionCfgFacade.findAll();

		List<SubmissionDetailsCfg> allSubmissionDetailsCfg = new ArrayList<>();
		for (SubmissionCfg var : listSubmissionCfg) {

			allSubmissionDetailsCfg.addAll(var.getSubmissionDetailsList());

		}

		return allSubmissionDetailsCfg;

	}
}
