package cfg.facade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.facade.AbstractFacade;

import cfg.model.SubmissionCfg;

@Service
@Transactional
public class SubmissionCfgFacade extends AbstractFacade<SubmissionCfg> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SubmissionCfgFacade() {
		super(SubmissionCfg.class);
	}

	public void insertfSubmissionCfgIfNotExist(String submissionName) throws Exception {

		List<SubmissionCfg> listSubmissionCfg =

				getEntityManager().createNamedQuery(SubmissionCfg.CHECK_IF_SUBMISSION_EXISTS, SubmissionCfg.class)
						.setParameter("name", submissionName).getResultList();

		if (listSubmissionCfg.isEmpty()) {
			SubmissionCfg submissionCfg = new SubmissionCfg();
			submissionCfg.setName(submissionName);
			submissionCfg.setCreationDate(new Date());
			this.save(submissionCfg);
		}

	}
}
