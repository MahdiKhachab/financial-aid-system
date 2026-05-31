package org.facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.dtos.CompanyDetailsDto;
import org.model.Mediator;
import org.model.Sponsor;

import cfg.model.CompanyCfg;
import enums.CurrencyEnum;

@Stateless
@LocalBean
public class MediatorFacade extends AbstractFacade<Mediator> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MediatorFacade() {
		super(Mediator.class);

	}

	public List<String> getAllNames() {

		TypedQuery<String> query = getEntityManager().createQuery("select m.delegateName from Mediator m ",
				String.class);
		return query.getResultList();
	}

	public List<Mediator> reportMediatorByName(Date from, Date to, String name, Sponsor sponsr) {

		StringBuilder query = new StringBuilder();

		StringBuilder where = new StringBuilder();

		query.append(" select m from Mediator m ");

		if (sponsr == null)
			where.append(" m.delegateName LIKE CONCAT('%',:name,'%') ");
		else
			where.append(" m.fromSponsor = :sponsr ");

		if (from != null)
			where.append(" and m.receiveDate >= :fromDate ");
		if (to != null)
			where.append(" and m.receiveDate <= :toDate ");

		query.append(" where  " + where);

		try {

			TypedQuery<Mediator> typedQuery = getEntityManager().createQuery(query.toString(), Mediator.class);

			if (sponsr == null)
				typedQuery.setParameter("name", name.trim());
			else
				typedQuery.setParameter("sponsr", sponsr);

			if (from != null)
				typedQuery.setParameter("fromDate", from);

			if (to != null)
				typedQuery.setParameter("toDate", to);

			System.out.println(getSqlFromJpql(typedQuery));

			return typedQuery.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Mediator> reportMediatorByCompany(Date from, Date to, CompanyCfg company) {

		StringBuilder query = new StringBuilder();

		StringBuilder where = new StringBuilder();

		query.append(" select m from Mediator m ");

		where.append(" m.toCompany = :company ");

		if (from != null)
			where.append(" and  m.receiveDate >= :fromDate ");
		if (to != null)
			where.append(" and m.receiveDate <= :toDate ");

		query.append(" where  " + where);

		try {

			TypedQuery<Mediator> typedQuery = getEntityManager().createQuery(query.toString(), Mediator.class);

			typedQuery.setParameter("company", company);

			if (from != null)
				typedQuery.setParameter("fromDate", from, TemporalType.DATE);

			if (to != null)
				typedQuery.setParameter("toDate", to, TemporalType.DATE);

			System.out.println(getSqlFromJpql(typedQuery));

			return typedQuery.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public double findAllNotDeliveredDD() {
		try {
			TypedQuery<Double> query = getEntityManager().createNamedQuery(Mediator.NOTDELIVEREDDOLLARS, Double.class);
			query.setParameter("dd", CurrencyEnum.DD);

			if (query.getSingleResult() != null) {
				return query.getSingleResult();
			} else
				return 0.0;

		} catch (Exception e) {
			return 0.0;
		}
	}

	public double findAllNotDeliveredLira() {
		try {
			TypedQuery<Double> query = getEntityManager().createNamedQuery(Mediator.NOTDELIVEREDLIRA, Double.class);
			query.setParameter("ll", CurrencyEnum.Lira);
			if (query.getSingleResult() != null) {
				return query.getSingleResult();
			} else
				return 0.0;
		} catch (Exception e) {
			return 0.0;
		}
	}

	public List<CompanyDetailsDto> findAllNotDeliveredWithCompany(CurrencyEnum currencyEnum) {
		try {
			TypedQuery<CompanyDetailsDto> query = getEntityManager().createNamedQuery(Mediator.NOTDELIVEREDWITHCOMPANY,
					CompanyDetailsDto.class);
			
			query.setParameter("currency", currencyEnum);

			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<CompanyDetailsDto>();
		}
	}

}
