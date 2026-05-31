package org.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.MediatorFacade;
import org.facade.SponsorFacade;
import org.model.Attachment;
import org.model.Mediator;
import org.model.Sponsor;
import org.omnifaces.util.Faces;

import cfg.facade.CompanyCfgFacade;
import cfg.model.CompanyCfg;
import interfaces.ICRUDOperations;
import utils.CommonUtility;
import utils.Utility;

@Named
@ViewScoped
public class MediatorListController extends AbstractController<Mediator> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Mediator mediator = new Mediator();
	private List<Mediator> pageItems = new ArrayList<Mediator>();
	private List<Mediator> filteredList;

	private List<Sponsor> sponsors = new ArrayList<Sponsor>();

	private List<CompanyCfg> companies = new ArrayList<>();

	@Inject
	private SponsorFacade sponsorFacade;
	@Inject
	private CompanyCfgFacade companyFacade;
	@Inject
	private MediatorFacade mediatorFacade;

	@PostConstruct
	public void init() {
		sponsors = sponsorFacade.findAll();
		companies = companyFacade.findAll();
		pageItems = getAbstractFacade().findAll();
	}

	public void delete(Mediator mediator) {
		try {
			getAbstractFacade().remove(mediator);
			this.pageItems.remove(mediator);
			CommonUtility.addMessageToFacesContextFromBundle("update_succes", "resources.Bundle", "success");
		} catch (Exception e) {
			Utility.addWarningMessage(e.getMessage());
		}
	}

	public void select(Mediator mediator) {
		this.mediator = mediator;
	}


	public void deleteAttachment(Attachment attachment) {
		try {
			this.mediator.removeFromAttachment(attachment);
			mediator = this.mediatorFacade.save(mediator);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void download(Attachment attachment) {
		try {
			Faces.sendFile(attachment.getData(), attachment.getFileName(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Mediator getMediator() {
		return mediator;
	}

	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}

	public List<Mediator> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<Mediator> pageItems) {
		this.pageItems = pageItems;
	}

	public List<Mediator> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<Mediator> filteredList) {
		this.filteredList = filteredList;
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

	public int getCountItems() {
		return pageItems.size();
	}

	@Override
	protected Mediator getItem() {
		return this.mediator;
	}

	@Override
	protected void setItem(Mediator item) {
		this.mediator = item;
	}

	@Override
	protected long getId() {
		return this.mediator.getId();
	}

	@Override
	protected ICRUDOperations<Mediator> getAbstractFacade() {
		return this.mediatorFacade;
	}

}
