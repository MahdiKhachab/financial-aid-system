package org.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import cfg.model.CompanyCfg;


@NamedQueries({
	
	@NamedQuery(name = ProvideAidCompany.GET_ALL_AID_BY_COMPANY, query = "select p from ProvideAidCompany p where p.toCompany = :company ") })
@NamedQuery(name = ProvideAidCompany.GET_ALL_AID_BY_COMPANY_WITH_DATE, query = "select p from   ProvideAidCompany p where   p.deliveryDate between :dateFrom and :dateTo and p.submissionDetailsCfg = :submissionDetailsCfg")


@Entity
public class ProvideAidCompany extends ProvideAid implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final   String GET_ALL_AID_BY_COMPANY =  "Get_All_Aid_By_Company" ;
	
	public static final   String GET_ALL_AID_BY_COMPANY_WITH_DATE =  "Get_All_Aid_By_Company_With_Date" ;

	
	@ManyToOne
	@JoinColumn(name = "COMPANY_ID")
	private CompanyCfg toCompany;

	private String delegate;

	public CompanyCfg getToCompany() {
		return toCompany;
	}

	public void setToCompany(CompanyCfg toCompany) {
		this.toCompany = toCompany;
	}

	public String getDelegate() {
		return delegate;
	}

	public void setDelegate(String delegate) {
		this.delegate = delegate;
	}

}
