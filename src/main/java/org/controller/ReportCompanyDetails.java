package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.dtos.CompanyDetailsDto;
import org.facade.MediatorFacade;

import enums.CurrencyEnum;
import utils.Utility;

@Named
@ViewScoped
public class ReportCompanyDetails implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Inject
	private MediatorFacade mediatorFacade;

	List<CompanyDetailsDto> pageItems = new ArrayList<CompanyDetailsDto>();

	private CurrencyEnum currency = CurrencyEnum.DD;

	@PostConstruct
	public void init() {
	}

	public List<CompanyDetailsDto> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<CompanyDetailsDto> pageItems) {
		this.pageItems = pageItems;
	}

	public CurrencyEnum getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyEnum currency) {
		this.currency = currency;
	}
	
	public void search() {
		pageItems = mediatorFacade.findAllNotDeliveredWithCompany(currency);
		Utility.addSuccessMessage("search_completed");
	}

}
