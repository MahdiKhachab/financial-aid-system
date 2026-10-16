package org.facade;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cfg.facade.SubmissionCfgFacade;
import cfg.model.SubmissionCfg;
import cfg.model.SubmissionDetailsCfg;

@Service
@Transactional
public class ExternalRepportFacade {

	@Inject
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
