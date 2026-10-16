package org.controller;

import java.io.UncheckedIOException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.facade.ReceiveMoneyFacade;
import org.facade.SponsorFacade;
import org.model.ReceiveMoney;
import org.model.Sponsor;
import org.omnifaces.util.Faces;

import cfg.model.SubmissionDetailsCfg;
import enums.CurrencyEnum;
import interfaces.ICRUDOperations;
import utils.Utility;

@ViewScoped
@Named
public class RepportSponsorController extends AbstractController<ReceiveMoney> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ReceiveMoneyFacade receiveMoneyFacade;

	@Inject
	private SponsorFacade sponsorFacade;

	private ReceiveMoney receiveMoney;

	private List<SubmissionDetailsCfg> listSubmissionDetailsCfg = new ArrayList<>();
	private List<ReceiveMoney> filteredList;

	private List<ReceiveMoney> listReceiveMoney = new ArrayList<>();

	private final String REQUEST_PARAM = "id";

	private Sponsor sponsor;

	private Date fromDate, toDate;

	private SubmissionDetailsCfg submissionDetailsCfg;
	
	private double totalValueLL ;
	private double totalValueDD ;

	@PostConstruct
	public void init() {
		listSubmissionDetailsCfg = receiveMoneyFacade.getAllSubmissionDetails();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				sponsor = sponsorFacade.find(Long.parseLong(id));
				if (sponsor == null) {
					try {
						Faces.redirect("exception");
					} catch (UncheckedIOException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}

	public void fillRepport() {

		if (   fromDate!=null && toDate !=null  && fromDate.getTime() > toDate.getTime()) {
			Utility.addWarningMessage("from_date_bigger_to_date");

		} else {

			listReceiveMoney = receiveMoneyFacade.fillReport(sponsor, fromDate, toDate, submissionDetailsCfg);

			if (listReceiveMoney == null || listReceiveMoney.isEmpty()) {
				Utility.addWarningMessage("no_value_sponsor_report");
			}

		}
		
		
		totalValueLL = listReceiveMoney.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.Lira))
				.mapToDouble(x -> x.getValue()).sum();
		
		
		
		
		totalValueDD= listReceiveMoney.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.DD))
				.mapToDouble(x -> x.getValue()).sum();
		
		Utility.addSuccessMessage("search_completed");
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

	public SubmissionDetailsCfg getSubmissionDetailsCfg() {
		return submissionDetailsCfg;
	}

	public void setSubmissionDetailsCfg(SubmissionDetailsCfg submissionDetailsCfg) {
		this.submissionDetailsCfg = submissionDetailsCfg;
	}

	public ReceiveMoney getReceiveMoney() {
		return receiveMoney;
	}

	public void setReceiveMoney(ReceiveMoney receiveMoney) {
		this.receiveMoney = receiveMoney;
	}

	public List<SubmissionDetailsCfg> getListSubmissionDetailsCfg() {
		return listSubmissionDetailsCfg;
	}

	public void setListSubmissionDetailsCfg(List<SubmissionDetailsCfg> listSubmissionDetailsCfg) {
		this.listSubmissionDetailsCfg = listSubmissionDetailsCfg;
	}

	public List<ReceiveMoney> getListReceiveMoney() {
		return listReceiveMoney;
	}

	public void setListReceiveMoney(List<ReceiveMoney> listReceiveMoney) {
		this.listReceiveMoney = listReceiveMoney;
	}

	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	// overide
	@Override
	protected ICRUDOperations<ReceiveMoney> getAbstractFacade() {
		return receiveMoneyFacade;
	}

	@Override
	protected ReceiveMoney getItem() {
		return receiveMoney;
	}

	@Override
	protected void setItem(ReceiveMoney item) {

		this.receiveMoney = item;

	}

	@Override
	protected long getId() {
		return 0;
	}

	public List<ReceiveMoney> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<ReceiveMoney> filteredList) {
		this.filteredList = filteredList;
	}

	public double getTotalValueLL() {
		return totalValueLL;
	}


	public void setTotalValueLL(double totalValueLL) {
		this.totalValueLL = totalValueLL;
	}


	public double getTotalValueDD() {
		return totalValueDD;
	}


	public void setTotalValueDD(double totalValueDD) {
		this.totalValueDD = totalValueDD;
	}
	
	
}
