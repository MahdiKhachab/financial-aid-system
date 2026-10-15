package org.dtos;

import java.io.Serializable;

import cfg.model.CompanyCfg;
import enums.CurrencyEnum;

public class CompanyDetailsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CompanyCfg name;

	private double total;

	private CurrencyEnum currency;

	public CompanyDetailsDto() {
	}

	public CompanyDetailsDto(CompanyCfg name, double total, CurrencyEnum currency) {
		super();
		this.name = name;
		this.total = total;
		this.currency = currency;
	}

	public CompanyCfg getName() {
		return name;
	}

	public void setName(CompanyCfg name) {
		this.name = name;
	}

	public CurrencyEnum getCurrency() {
		return currency;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void setCurrency(CurrencyEnum currency) {
		this.currency = currency;
	}

}
