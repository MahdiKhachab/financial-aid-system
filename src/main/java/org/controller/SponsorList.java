package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.SponsorFacade;
import org.model.Sponsor;

import cfg.facade.ReligionReferenceCfgFacade;
import cfg.model.ReligionReferenceCfg;
import interfaces.ICRUDOperations;
import utils.Utility;

@ViewScoped
@Named
public class SponsorList extends AbstractController<Sponsor> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Sponsor sponser;
	private List<Sponsor> pageItems = new ArrayList<Sponsor>();
	private List<Sponsor> filteredList;
	private List<ReligionReferenceCfg> religionReferenceCfgList = new ArrayList<>();

	@Inject
	SponsorFacade sponsorFacade;

	@Inject
	ReligionReferenceCfgFacade religionReferenceCfgFacade;

	@PostConstruct
	public void init() {

		pageItems = getAbstractFacade().findAll();
		religionReferenceCfgList = religionReferenceCfgFacade.findAll();

	}

	public void delete(Sponsor sponsor) {
		try {
				getAbstractFacade().remove(sponsor);
				pageItems = getAbstractFacade().findAll();
			
			Utility.addSuccessMessage("delete_succes");

		} catch (Exception e) {

			//if(e instanceof )
			Utility.addErrorMessage("cant_delete_this_card_has_rel_with_other_table");
		}
	}

	public List<ReligionReferenceCfg> getReligionReferenceCfgList() {
		return religionReferenceCfgList;
	}

	public void setReligionReferenceCfgList(List<ReligionReferenceCfg> religionReferenceCfgList) {
		this.religionReferenceCfgList = religionReferenceCfgList;
	}

	public int getCountItems() {
		return pageItems.size();
	}

	public Sponsor getSponsor() {
		return sponser;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponser = sponsor;
	}

	@Override
	protected Sponsor getItem() {

		return sponser;
	}

	@Override
	protected void setItem(Sponsor item) {
		this.sponser = item;
	}

	@Override
	protected long getId() {

		return 0;
	}

	@Override
	protected ICRUDOperations<Sponsor> getAbstractFacade() {

		return sponsorFacade;
	}

	public Sponsor getSponser() {
		return sponser;
	}

	public void setSponser(Sponsor sponser) {
		this.sponser = sponser;
	}

	public List<Sponsor> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<Sponsor> pageItems) {
		this.pageItems = pageItems;
	}

	public List<Sponsor> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<Sponsor> filteredList) {
		this.filteredList = filteredList;
	}

}
