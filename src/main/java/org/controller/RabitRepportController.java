package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.facade.ProvideAidFacade;
import org.model.ProvideAidRabet;

import cfg.model.SubmissionDetailsCfg;
import enums.CurrencyEnum;
import utils.Utility;

@ViewScoped
@Named

public class RabitRepportController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private ProvideAidFacade provideAidFacade;

	private List<ProvideAidRabet> filteredList;
	private String REQUEST_PARAM = "name";

	private List<ProvideAidRabet> pageItems = new ArrayList<>();
	private List<ProvideAidRabet> listOrginal = new ArrayList<>();

	private String rabitQaryeName;
	private List<SubmissionDetailsCfg> listSubmissionDetailsCfg = new ArrayList<>();

	
	private Date dateFrom;
	private Date dateTo;
	private double totalValueLL;
	private double totalValueDD;
	private SubmissionDetailsCfg submissionDetailsCfg;

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		listSubmissionDetailsCfg = provideAidFacade.getAllSubmissionDetails();

		if (!facesContext.isPostback()) {
			rabitQaryeName = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (rabitQaryeName != null) {
				listOrginal = provideAidFacade.getAidRabit(rabitQaryeName);
				pageItems = new ArrayList<>(listOrginal);
				totalValueLL = pageItems.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.Lira))
						.mapToDouble(x -> x.getValue()).sum();

				totalValueDD = pageItems.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.DD))
						.mapToDouble(x -> x.getValue()).sum();
			}
		}
	}
	
	
	public void filterByDate() {

		if (dateFrom != null && dateTo != null  ) {
			if (dateFrom.getTime() > dateTo.getTime()) {
				Utility.addWarningMessage("from_date_bigger_to_date");
			} 
			
			else {
				if(submissionDetailsCfg==null) {
				pageItems = listOrginal.stream()
						.filter(x -> x.getDeliveryDate().getTime() >= dateFrom.getTime()
								&& x.getDeliveryDate().getTime() <= dateTo.getTime())
						.collect(Collectors.toList());

			}
				
				else if ( submissionDetailsCfg !=null) {
					pageItems = listOrginal.stream()
							.filter(x -> x.getDeliveryDate().getTime() >= dateFrom.getTime()
									&& x.getDeliveryDate().getTime() <= dateTo.getTime() && x.getSubmissionDetailsCfg().getType() .equals(submissionDetailsCfg.getType())  )
							.collect(Collectors.toList());
				}
				}

		}

		else if (submissionDetailsCfg != null &&   dateFrom == null && dateTo == null ) {
			pageItems = listOrginal.stream()
					.filter(x ->  x.getSubmissionDetailsCfg().getType() .equals(submissionDetailsCfg.getType())  )
					.collect(Collectors.toList());

		}
		totalValueLL = pageItems.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.Lira))
				.mapToDouble(x -> x.getValue()).sum();

		totalValueDD = pageItems.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.DD))
				.mapToDouble(x -> x.getValue()).sum();
	}

	public int getCountItems() {
		return pageItems.size();
	}

	public List<ProvideAidRabet> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<ProvideAidRabet> filteredList) {
		this.filteredList = filteredList;
	}

	public List<ProvideAidRabet> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<ProvideAidRabet> pageItems) {
		this.pageItems = pageItems;
	}

	public String getRabitQaryeName() {
		return rabitQaryeName;
	}

	public void setRabitQaryeName(String rabitQaryeName) {
		this.rabitQaryeName = rabitQaryeName;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
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


	public SubmissionDetailsCfg getSubmissionDetailsCfg() {
		return submissionDetailsCfg;
	}


	public void setSubmissionDetailsCfg(SubmissionDetailsCfg submissionDetailsCfg) {
		this.submissionDetailsCfg = submissionDetailsCfg;
	}


	public List<ProvideAidRabet> getListOrginal() {
		return listOrginal;
	}


	public void setListOrginal(List<ProvideAidRabet> listOrginal) {
		this.listOrginal = listOrginal;
	}


	public List<SubmissionDetailsCfg> getListSubmissionDetailsCfg() {
		return listSubmissionDetailsCfg;
	}


	public void setListSubmissionDetailsCfg(List<SubmissionDetailsCfg> listSubmissionDetailsCfg) {
		this.listSubmissionDetailsCfg = listSubmissionDetailsCfg;
	}
	
	

}
