package org.facade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import org.model.ReceiveMoney;
import org.model.Sponsor;

import cfg.facade.SubmissionDetailsCfgFacade;
import cfg.model.SubmissionDetailsCfg;
import enums.CurrencyEnum;

@Stateless
@LocalBean

public class ReceiveMoneyFacade extends AbstractFacade<ReceiveMoney> implements Serializable {

	/**
	 * 
	 */

	@Inject

	private SubmissionDetailsCfgFacade submissionDetailsCfgFacade;
	private static final long serialVersionUID = 1L;

	public ReceiveMoneyFacade() {
		super(ReceiveMoney.class);
	}

	public List<SubmissionDetailsCfg> getAllSubmissionDetails() {
		return submissionDetailsCfgFacade.findAll();
	}

	public void saveList(List<ReceiveMoney> ListreceiveMoney, Date date, Sponsor sponsor) throws Exception {

		for (ReceiveMoney var : ListreceiveMoney) {
			var.setDate(date);
			submissionDetailsCfgFacade.updateValuesFromSponsor(var.getSubmissionDetailsCfg(), var.getValue(),
					var.getCurrencyEnum().equals(CurrencyEnum.Lira) ? true : false);
			var.setSponser(sponsor);
			this.save(var);

		}

	}

	public void updateValue() {

	}

	public List<ReceiveMoney> fillReport(Sponsor sponosr, Date fromDate, Date toDate,
			SubmissionDetailsCfg submissionDetailsCfg) {

		try {

			StringBuilder query = new StringBuilder();

			StringBuilder where = new StringBuilder();
			query.append(" SELECT re FROM ReceiveMoney re");
			if (sponosr != null) {
				where.append("  where re.sponser = :sponsor ");

			}
			else {
				where.append("  where  re.sponser = re.sponser  ") ;
			}

			if (fromDate != null)
				where.append(" and re.date   >= :fromDate ");
			if (toDate != null)
				where.append(" and  re.date  <= :toDate ");
			if (submissionDetailsCfg != null) {
				where.append(" and re.submissionDetailsCfg  =  :submissionDetailsCfg");
			}
			query.append(where);
			TypedQuery<ReceiveMoney> typedQuery = getEntityManager().createQuery(query.toString(), ReceiveMoney.class);

			if (sponosr != null) {
			typedQuery.setParameter("sponsor", sponosr);
			}
			if (fromDate != null)
				typedQuery.setParameter("fromDate", fromDate);

			if (toDate != null)
				typedQuery.setParameter("toDate", toDate);
			if (submissionDetailsCfg != null) {
				typedQuery.setParameter("submissionDetailsCfg", submissionDetailsCfg);
			}

			return typedQuery.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
