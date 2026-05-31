package org.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.ProvideAidFacade;
import org.model.ProvideAidCompany;
import org.model.ProvideAidRabet;
import org.omnifaces.util.Faces;

import cfg.facade.CompanyCfgFacade;
import cfg.model.CompanyCfg;
import cfg.model.SubmissionDetailsCfg;
import enums.CurrencyEnum;
import utils.Utility;

@ViewScoped
@Named
public class CompanayRepportContoller implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SubmissionDetailsCfg> listSubmissionDetailsCfg = new ArrayList<>();
	@Inject
	private ProvideAidFacade provideAidFacade;

	private List<ProvideAidCompany> pageItems = new ArrayList<>();
	private List<ProvideAidCompany> listOrginal = new ArrayList<>();
	private List<ProvideAidCompany> filteredList;

	@Inject
	private CompanyCfgFacade companyCfgFacade;
	private CompanyCfg companyCfg;

	private Date dateFrom;
	private Date dateTo;
	private double totalValueLL;
	private double totalValueDD;
	private SubmissionDetailsCfg submissionDetailsCfg;

	private String REQUEST_PARAM = "id";

	@PostConstruct
	public void init() {
		listSubmissionDetailsCfg = provideAidFacade.getAllSubmissionDetails();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				companyCfg = companyCfgFacade.find(Long.parseLong(id));
				listOrginal = provideAidFacade.getAllAidToCompany(companyCfg);
				pageItems = new ArrayList<>(listOrginal);

				totalValueLL = pageItems.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.Lira))
						.mapToDouble(x -> x.getValue()).sum();

				totalValueDD = pageItems.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.DD))
						.mapToDouble(x -> x.getValue()).sum();

				if (companyCfg == null) {
					try {
						Faces.redirect("exception");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}

	public void filterByDate() {

		pageItems = listOrginal;

		if (dateFrom != null && dateTo != null && dateFrom.after(dateTo)) {
			Utility.addWarningMessage("from_date_bigger_to_date");
			return;
		}

		if (submissionDetailsCfg == null) {

			if (dateFrom != null && dateTo != null) {

				pageItems = listOrginal.stream().filter(x -> Utility.startDate(x.getDeliveryDate()).getTime() >= Utility.startDate(dateFrom).getTime()
						&& x.getDeliveryDate().getTime() <= Utility.startDate(dateTo).getTime()).collect(Collectors.toList());

			} else if (dateFrom != null && dateTo == null) {
				pageItems = listOrginal.stream().filter(x -> Utility.startDate(x.getDeliveryDate()).getTime() >= Utility.startDate(dateFrom).getTime())
						.collect(Collectors.toList());

			} else if (dateFrom == null && dateTo != null) {
				pageItems = listOrginal.stream().filter(x ->Utility.startDate(x.getDeliveryDate()).getTime() <= Utility.startDate(dateTo).getTime())
						.collect(Collectors.toList());
			}

		} else {

			if (dateFrom != null && dateTo != null) {
				pageItems = listOrginal.stream().filter(
						x -> Utility.startDate(x.getDeliveryDate()).getTime() >= Utility.startDate(dateFrom).getTime()
								&& Utility.startDate(x.getDeliveryDate()).getTime() <= Utility.startDate(dateTo)
										.getTime()
								&& x.getSubmissionDetailsCfg().getType().equals(submissionDetailsCfg.getType()))
						.collect(Collectors.toList());

			} else if (dateFrom == null && dateTo == null) {
				pageItems = listOrginal.stream()
						.filter(x -> x.getSubmissionDetailsCfg().getType().equals(submissionDetailsCfg.getType()))
						.collect(Collectors.toList());

			} else if (dateFrom != null && dateTo == null) {
				pageItems = listOrginal.stream().filter(
						x -> Utility.startDate(x.getDeliveryDate()).getTime() >= Utility.startDate(dateFrom).getTime()
								&& x.getSubmissionDetailsCfg().getType().equals(submissionDetailsCfg.getType()))
						.collect(Collectors.toList());

			} else if (dateFrom == null && dateTo != null) {
				pageItems = listOrginal.stream().filter(
						x -> Utility.startDate(x.getDeliveryDate()).getTime() <= Utility.startDate(dateTo).getTime()
								&& x.getSubmissionDetailsCfg().getType().equals(submissionDetailsCfg.getType()))
						.collect(Collectors.toList());
			}

		}

		totalValueLL = pageItems.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.Lira))
				.mapToDouble(x -> x.getValue()).sum();

		totalValueDD = pageItems.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.DD))
				.mapToDouble(x -> x.getValue()).sum();

		Utility.addSuccessMessage("search_completed");
	}

	public List<SubmissionDetailsCfg> getListSubmissionDetailsCfg() {
		return listSubmissionDetailsCfg;
	}

	public void setListSubmissionDetailsCfg(List<SubmissionDetailsCfg> listSubmissionDetailsCfg) {
		this.listSubmissionDetailsCfg = listSubmissionDetailsCfg;
	}

	public List<ProvideAidCompany> getPageItems() {
		return pageItems;
	}

	public SubmissionDetailsCfg getSubmissionDetailsCfg() {
		return submissionDetailsCfg;
	}

	public void setSubmissionDetailsCfg(SubmissionDetailsCfg submissionDetailsCfg) {
		this.submissionDetailsCfg = submissionDetailsCfg;
	}

	public void setPageItems(List<ProvideAidCompany> pageItems) {
		this.pageItems = pageItems;
	}

	public List<ProvideAidCompany> getListOrginal() {
		return listOrginal;
	}

	public void setListOrginal(List<ProvideAidCompany> listOrginal) {
		this.listOrginal = listOrginal;
	}

	public List<ProvideAidCompany> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<ProvideAidCompany> filteredList) {
		this.filteredList = filteredList;
	}

	public CompanyCfg getCompanyCfg() {
		return companyCfg;
	}

	public void setCompanyCfg(CompanyCfg companyCfg) {
		this.companyCfg = companyCfg;
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

}
