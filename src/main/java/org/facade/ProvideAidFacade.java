package org.facade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.model.Deserved;
import org.model.ProvideAid;
import org.model.ProvideAidCompany;
import org.model.ProvideAidDesrvedPerson;
import org.model.ProvideAidRabet;

import cfg.facade.SubmissionDetailsCfgFacade;
import cfg.model.CompanyCfg;
import cfg.model.SubmissionDetailsCfg;
import enums.CurrencyEnum;

@Stateless
@LocalBean
public class ProvideAidFacade extends AbstractFacade<ProvideAid> implements Serializable {
	@Inject

	private SubmissionDetailsCfgFacade submissionDetailsCfgFacade;
	private static final long serialVersionUID = 1L;

	public ProvideAidFacade() {
		super(ProvideAid.class);
	}

	public List<SubmissionDetailsCfg> getAllSubmissionDetails() {
		return submissionDetailsCfgFacade.findAll();
	}

	/*
	 * Rabit Repport
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllNamesRabit() {

		try {
			Query query = getEntityManager().createNamedQuery(ProvideAidRabet.GET_RABIT_NAME);
			return query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public List<ProvideAidRabet> getAidRabit(String name) {

		try {
			TypedQuery<ProvideAidRabet> query = getEntityManager().createNamedQuery(ProvideAidRabet.GET_All_RABIT_GIVES,
					ProvideAidRabet.class);
			query.setParameter("name", name);

			return query.getResultList();

		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * / end rabit repport
	 */

	/*
	 * /
	 */

	/*
	 * Company repport
	 */

	public List<ProvideAidCompany> getAllAidToCompany(CompanyCfg company) {

		try {
			TypedQuery<ProvideAidCompany> query = getEntityManager()
					.createNamedQuery(ProvideAidCompany.GET_ALL_AID_BY_COMPANY, ProvideAidCompany.class);
			query.setParameter("company", company);

			return query.getResultList();

		} catch (Exception e) {
			return null;
		}

	}
	/*
	 * */

	public List<ProvideAidDesrvedPerson> findPersonDesrvedWithoutDate(Deserved dersved) {
		try {
			TypedQuery<ProvideAidDesrvedPerson> query = getEntityManager().createNamedQuery(
					ProvideAidDesrvedPerson.PERSON_DESRVED_WITHOUT_DATE, ProvideAidDesrvedPerson.class);
			query.setParameter("desrvedPerson", dersved);

			return query.getResultList();

		} catch (Exception e) {
			return null;
		}
	}

	
	
	public List<ProvideAidDesrvedPerson> findPersonDesrvedWithDate(SubmissionDetailsCfg  submissionDetailsCfg , Date dateFrom, Date dateTo) {
		try {
			TypedQuery<ProvideAidDesrvedPerson> query = getEntityManager()
					.createNamedQuery(ProvideAidDesrvedPerson.PERSON_DESRVED_WITH_DATE, ProvideAidDesrvedPerson.class);

			query.setParameter("dateFrom", dateFrom);
			query.setParameter("dateTo", dateTo);
			
			query.setParameter("submissionDetailsCfg", submissionDetailsCfg);

			

			return query.getResultList();

		} catch (Exception e) {
			return null;
		}
	}

	
	
	
	public List<ProvideAidRabet> findRabitAidWithDate(SubmissionDetailsCfg  submissionDetailsCfg , Date dateFrom, Date dateTo) {
		try {
			TypedQuery<ProvideAidRabet> query = getEntityManager()
					.createNamedQuery(ProvideAidRabet.GET_All_RABIT_WITHDATE, ProvideAidRabet.class);

			query.setParameter("dateFrom", dateFrom);
			query.setParameter("dateTo", dateTo);
			
			query.setParameter("submissionDetailsCfg", submissionDetailsCfg);

			

			return query.getResultList();

		} catch (Exception e) {
			return null;
		}
	}
	
	
	
	public List<ProvideAidCompany> findCompanyAidWithDate(SubmissionDetailsCfg  submissionDetailsCfg , Date dateFrom, Date dateTo) {
		try {
			TypedQuery<ProvideAidCompany> query = getEntityManager()
					.createNamedQuery(ProvideAidCompany.GET_ALL_AID_BY_COMPANY_WITH_DATE, ProvideAidCompany.class);

			query.setParameter("dateFrom", dateFrom);
			query.setParameter("dateTo", dateTo);
			
			query.setParameter("submissionDetailsCfg", submissionDetailsCfg);

			

			return query.getResultList();

		} catch (Exception e) {
			return null;
		}
	}
	
	
	public void saveListFromDesrvedPerson(List<ProvideAidDesrvedPerson> provideAidList, Date dateReceive,
			Deserved deserved) throws Exception {
		for (ProvideAidDesrvedPerson provideAid : provideAidList) {
			provideAid.setDesrvedPerson(deserved);
			provideAid.setDeliveryDate(dateReceive);
			submissionDetailsCfgFacade.updateValueFromAid(provideAid.getSubmissionDetailsCfg(), provideAid.getValue(),
					provideAid.getCurrencyEnum().equals(CurrencyEnum.Lira) ? true : false);
			this.save(provideAid);
		}
	}

	public void saveListFromDesrvedRabit(List<ProvideAidRabet> provideAidRabetList, Date dateReceive, String rabitName,
			String delegate) throws Exception {
		for (ProvideAidRabet obj : provideAidRabetList) {
			obj.setRabetName(rabitName);
			obj.setDelagate(delegate);
			obj.setDeliveryDate(dateReceive);
			submissionDetailsCfgFacade.updateValueFromAid(obj.getSubmissionDetailsCfg(), obj.getValue(),
					obj.getCurrencyEnum().equals(CurrencyEnum.Lira) ? true : false);
			this.save(obj);
		}
	}

	public void saveListFromDesrvedCompany(List<ProvideAidCompany> provideAidCompanyList, Date dateReceive,
			CompanyCfg selectedCompanyCfg, String delegate) throws Exception {
		for (ProvideAidCompany obj : provideAidCompanyList) {
			obj.setToCompany(selectedCompanyCfg);
			obj.setDelegate(delegate);
			obj.setDeliveryDate(dateReceive);
			submissionDetailsCfgFacade.updateValueFromAid(obj.getSubmissionDetailsCfg(), obj.getValue(),
					obj.getCurrencyEnum().equals(CurrencyEnum.Lira) ? true : false);
			this.save(obj);
		}
	}

}
