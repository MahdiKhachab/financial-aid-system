package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.MediatorFacade;
import org.facade.SponsorFacade;
import org.model.Mediator;
import org.model.Sponsor;

import cfg.facade.CompanyCfgFacade;
import cfg.model.CompanyCfg;
import enums.CurrencyEnum;
import interfaces.ICRUDOperations;
import utils.Utility;

@Named
@ViewScoped
public class ReportMediatorController extends AbstractController<Mediator> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date fromDate;
	private Date toDate;
	private String name;
	private Sponsor sponsor;
	private CompanyCfg company;

	@Inject
	private MediatorFacade mediatorFacade;
	@Inject
	private SponsorFacade sponsorFacade;
	@Inject
	private CompanyCfgFacade companyFacade;

	private List<Mediator> listByName = new ArrayList<Mediator>();
	private List<Mediator> ListByCompany = new ArrayList<Mediator>();

	private List<Sponsor> sponsors = new ArrayList<Sponsor>();
	private List<CompanyCfg> companies = new ArrayList<>();

	private boolean byCompany = true;

	private double totalDollars;
	private double totalLira;

	private double notDeliveredLira;
	private double notDeliveredDollar;

	private List<String> nameList = new ArrayList<>();

	@PostConstruct
	public void inti() {
		sponsors = sponsorFacade.findAll();
		companies = companyFacade.findAll();
		nameList = mediatorFacade.getAllNames();
		notDeliveredDollar = mediatorFacade.findAllNotDeliveredDD();
		notDeliveredLira = mediatorFacade.findAllNotDeliveredLira();

	}

	public List<String> getNameList() {
		return nameList;
	}

	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}

	public double getNotDeliveredLira() {
		return notDeliveredLira;
	}

	public void setNotDeliveredLira(double notDeliveredLira) {
		this.notDeliveredLira = notDeliveredLira;
	}

	public double getNotDeliveredDollar() {
		return notDeliveredDollar;
	}

	public void setNotDeliveredDollar(double notDeliveredDollar) {
		this.notDeliveredDollar = notDeliveredDollar;
	}

	public boolean isByCompany() {
		return byCompany;
	}

	public void setByCompany(boolean byCompany) {
		this.byCompany = byCompany;
	}

	public void search() {
		totalLira = 0;
		totalDollars = 0;

		if (fromDate == null || toDate == null) {
			Utility.addErrorMessage("dates_are_required");
			return;
		}

		if (byCompany) {

			ListByCompany = mediatorFacade.reportMediatorByCompany(fromDate, toDate, company);

			if (ListByCompany != null && !ListByCompany.isEmpty()) {
				totalLira = ListByCompany.stream().filter(x -> x.getCurrency().equals(CurrencyEnum.Lira))
						.mapToDouble(x -> x.getAmmount()).sum();

				totalDollars = ListByCompany.stream().filter(x -> x.getCurrency().equals(CurrencyEnum.DD))
						.mapToDouble(x -> x.getAmmount()).sum();
			}

		} else {

			listByName = this.mediatorFacade.reportMediatorByName(fromDate, toDate, name, null);

			if (listByName != null && !listByName.isEmpty()) {
				totalLira = listByName.stream().filter(x -> x.getCurrency().equals(CurrencyEnum.Lira))
						.mapToDouble(x -> x.getAmmount()).sum();

				totalDollars = listByName.stream().filter(x -> x.getCurrency().equals(CurrencyEnum.DD))
						.mapToDouble(x -> x.getAmmount()).sum();
			}
		}
		Utility.addSuccessMessage("search_completed");

	}

	public double getTotalDollars() {
		return totalDollars;
	}

	public void setTotalDollars(double totalDollars) {
		this.totalDollars = totalDollars;
	}

	public double getTotalLira() {
		return totalLira;
	}

	public void setTotalLira(double totalLira) {
		this.totalLira = totalLira;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	public MediatorFacade getMediatorFacade() {
		return mediatorFacade;
	}

	public void setMediatorFacade(MediatorFacade mediatorFacade) {
		this.mediatorFacade = mediatorFacade;
	}

	public List<Mediator> getListByName() {
		return listByName;
	}

	public void setListByName(List<Mediator> listByName) {
		this.listByName = listByName;
	}

	public List<Mediator> getListByCompany() {
		return ListByCompany;
	}

	public void setListByCompany(List<Mediator> listByCompany) {
		ListByCompany = listByCompany;
	}

	public CompanyCfg getCompany() {
		return company;
	}

	public void setCompany(CompanyCfg company) {
		this.company = company;
	}

	public List<Sponsor> getSponsors() {
		return sponsors;
	}

	public void setSponsors(List<Sponsor> sponsors) {
		this.sponsors = sponsors;
	}

	public List<CompanyCfg> getCompanies() {
		return companies;
	}

	public void setCompanies(List<CompanyCfg> companies) {
		this.companies = companies;
	}

	@Override
	protected Mediator getItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setItem(Mediator item) {
		// TODO Auto-generated method stub

	}

	@Override
	protected long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected ICRUDOperations<Mediator> getAbstractFacade() {
		// TODO Auto-generated method stub
		return this.mediatorFacade;
	}

}
