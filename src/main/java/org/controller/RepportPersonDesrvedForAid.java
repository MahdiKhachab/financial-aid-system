package org.controller;

import java.io.UncheckedIOException;

import java.io.IOException;
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

import org.facade.DeservedFacade;
import org.facade.ProvideAidFacade;
import org.model.Deserved;
import org.model.ProvideAidDesrvedPerson;
import org.omnifaces.util.Faces;

import cfg.model.SubmissionDetailsCfg;
import enums.CurrencyEnum;
import utils.Utility;

@ViewScoped
@Named
public class RepportPersonDesrvedForAid implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProvideAidFacade provideAidFacade;

	@Inject
	DeservedFacade deservedFacade;

	private List<ProvideAidDesrvedPerson> pageItems = new ArrayList<>();

	private List<ProvideAidDesrvedPerson> listProvideAidDesrvedPerson = new ArrayList<>();

	private List<ProvideAidDesrvedPerson> filteredList;

	private Deserved deserved;

	private final String REQUEST_PARAM = "id";

	private List<SubmissionDetailsCfg> listSubmissionDetailsCfg = new ArrayList<>();

	private Date dateFrom;
	private Date dateTo;
	private double totalValueLL;
	private double totalValueDD;
	private SubmissionDetailsCfg submissionDetailsCfg;

	@PostConstruct
	public void init() {
		listSubmissionDetailsCfg = provideAidFacade.getAllSubmissionDetails();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				deserved = deservedFacade.find(Long.parseLong(id));

				listProvideAidDesrvedPerson = provideAidFacade.findPersonDesrvedWithoutDate(deserved);
				pageItems = new ArrayList<>(listProvideAidDesrvedPerson);

				totalValueLL = pageItems.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.Lira))
						.mapToDouble(x -> x.getValue()).sum();

				totalValueDD = pageItems.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.DD))
						.mapToDouble(x -> x.getValue()).sum();

				if (deserved == null) {
					try {
						Faces.redirect("exception");
					} catch (UncheckedIOException e) {
						e.printStackTrace();
					}
				}
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
				pageItems = listProvideAidDesrvedPerson.stream()
						.filter(x -> x.getDeliveryDate().getTime() >= dateFrom.getTime()
								&& x.getDeliveryDate().getTime() <= dateTo.getTime())
						.collect(Collectors.toList());

			}
				
				else if ( submissionDetailsCfg !=null) {
					pageItems = listProvideAidDesrvedPerson.stream()
							.filter(x -> x.getDeliveryDate().getTime() >= dateFrom.getTime()
									&& x.getDeliveryDate().getTime() <= dateTo.getTime() && x.getSubmissionDetailsCfg().getType() .equals(submissionDetailsCfg.getType())  )
							.collect(Collectors.toList());
				}
				}

		}

		else if (submissionDetailsCfg != null &&   dateFrom == null && dateTo == null ) {
			pageItems = listProvideAidDesrvedPerson.stream()
					.filter(x ->  x.getSubmissionDetailsCfg().getType() .equals(submissionDetailsCfg.getType())  )
					.collect(Collectors.toList());

		}
		totalValueLL = pageItems.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.Lira))
				.mapToDouble(x -> x.getValue()).sum();

		totalValueDD = pageItems.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.DD))
				.mapToDouble(x -> x.getValue()).sum();
	}

	public List<ProvideAidDesrvedPerson> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<ProvideAidDesrvedPerson> pageItems) {
		this.pageItems = pageItems;
	}

	public List<ProvideAidDesrvedPerson> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<ProvideAidDesrvedPerson> filteredList) {
		this.filteredList = filteredList;
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

	public Deserved getDeserved() {
		return deserved;
	}

	public void setDeserved(Deserved deserved) {
		this.deserved = deserved;
	}

	public List<SubmissionDetailsCfg> getListSubmissionDetailsCfg() {
		return listSubmissionDetailsCfg;
	}

	public void setListSubmissionDetailsCfg(List<SubmissionDetailsCfg> listSubmissionDetailsCfg) {
		this.listSubmissionDetailsCfg = listSubmissionDetailsCfg;
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

}
