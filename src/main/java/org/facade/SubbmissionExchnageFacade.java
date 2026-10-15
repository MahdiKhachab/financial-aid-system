package org.facade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import org.model.SubbmissionExchnage;

import cfg.facade.SubmissionDetailsCfgFacade;
import cfg.model.SubmissionDetailsCfg;

@Stateless
@LocalBean
public class SubbmissionExchnageFacade extends AbstractFacade<SubbmissionExchnage> implements Serializable {

	/**
	 * 
	 */
	@Inject
	private SubmissionDetailsCfgFacade submissionDetailsCfgFacade;
	private static final long serialVersionUID = 1L;

	public SubbmissionExchnageFacade() {
		super(SubbmissionExchnage.class);
	}

	public SubbmissionExchnage submit(SubbmissionExchnage subbmissionExchnage,
			SubmissionDetailsCfg submissionDetailsCfg) throws Exception {
		submissionDetailsCfgFacade.exchange(submissionDetailsCfg, subbmissionExchnage.liraValue,
				subbmissionExchnage.getDollarsValue());
		subbmissionExchnage.setSubmissionDetailsCfg(submissionDetailsCfg);
		return this.save(subbmissionExchnage);

	}

	public List<SubbmissionExchnage> subbmissionExchnageBtwTwoDate(Date fromDate, Date toDate,
			SubmissionDetailsCfg submissionDetailsCfg)   {


		TypedQuery<SubbmissionExchnage> query = getEntityManager()
				.createNamedQuery(SubbmissionExchnage.QUERY_BTW_TWO_DATE, SubbmissionExchnage.class);
		query.setParameter("submissionDetailsCfg", submissionDetailsCfg).setParameter("dateFrom", fromDate)
				.setParameter("dateTo", toDate);

		return query.getResultList();

	}

}
