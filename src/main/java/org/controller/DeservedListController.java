package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.DeservedFacade;
import org.facade.PurchaseVoucherFacade;
import org.model.Deserved;

import cfg.facade.HealthyStatusCfgFacade;
import cfg.facade.InssuranceCfgFacade;
import cfg.facade.PoorPercenatageCfgFacade;
import cfg.facade.ReligionReferenceCfgFacade;
import cfg.model.HealthyStatusCfg;
import cfg.model.InssuranceCfg;
import cfg.model.PoorPercentageCfg;
import cfg.model.ReligionReferenceCfg;
import enums.PersonGenre;
import interfaces.ICRUDOperations;
import utils.Utility;

@ViewScoped
@Named
public class DeservedListController extends AbstractController<Deserved> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private DeservedFacade deservedFacade;

	@Inject
	HealthyStatusCfgFacade healthyStatusCfgFacade;

	@Inject
	PoorPercenatageCfgFacade poorPercenatageCfgFacade;

	@Inject
	ReligionReferenceCfgFacade religionReferenceCfgFacade;

	@Inject
	InssuranceCfgFacade inssuranceCfgFacade;

	@Inject
	PurchaseVoucherFacade voucherFacade;

	private Deserved deserved;
	private List<Deserved> pageItems = new ArrayList<Deserved>();
	private List<Deserved> filteredList;

	private List<HealthyStatusCfg> healthyStatusList = new ArrayList<>();
	private List<InssuranceCfg> inssuranceCfgList = new ArrayList<>();
	private List<ReligionReferenceCfg> religionReferenceCfgList = new ArrayList<>();
	private List<PoorPercentageCfg> poorPercentageCfgList = new ArrayList<>();

	@PostConstruct
	public void init() {
		pageItems = getAbstractFacade().findAll();
		healthyStatusList = healthyStatusCfgFacade.findAll();
		inssuranceCfgList = inssuranceCfgFacade.findAll();
		religionReferenceCfgList = religionReferenceCfgFacade.findAll();
		poorPercentageCfgList = poorPercenatageCfgFacade.findAll();
	}

	public List<PersonGenre> getvalues() {
		return Arrays.asList(PersonGenre.values());
	}

	public List<HealthyStatusCfg> getHealthyStatusList() {
		return healthyStatusList;
	}

	public void setHealthyStatusList(List<HealthyStatusCfg> healthyStatusList) {
		this.healthyStatusList = healthyStatusList;
	}

	public List<InssuranceCfg> getInssuranceCfgList() {
		return inssuranceCfgList;
	}

	public void setInssuranceCfgList(List<InssuranceCfg> inssuranceCfgList) {
		this.inssuranceCfgList = inssuranceCfgList;
	}

	public List<ReligionReferenceCfg> getReligionReferenceCfgList() {
		return religionReferenceCfgList;
	}

	public void setReligionReferenceCfgList(List<ReligionReferenceCfg> religionReferenceCfgList) {
		this.religionReferenceCfgList = religionReferenceCfgList;
	}

	public List<PoorPercentageCfg> getPoorPercentageCfgList() {
		return poorPercentageCfgList;
	}

	public void setPoorPercentageCfgList(List<PoorPercentageCfg> poorPercentageCfgList) {
		this.poorPercentageCfgList = poorPercentageCfgList;
	}

	public int getCountItems() {
		return pageItems.size();
	}

	public void select(Deserved item) {
		this.deserved = item;
	}

	public void delete(Deserved deserved) {
		try {
			if (voucherFacade.checkIfRelatedWithDeserved(deserved)) {
				Utility.addErrorMessage("cant_remove_deserved_relation_id");
				return;
			}
			getAbstractFacade().remove(deserved);
			this.pageItems.remove(deserved);
			Utility.addSuccessMessage("delete_succes");
		} catch (Exception e) {
			Utility.addWarningMessage(e.getMessage());
		}
	}

	public List<Deserved> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<Deserved> filteredList) {
		this.filteredList = filteredList;
	}

	public Deserved getDeserved() {
		return deserved;
	}

	public void setDeserved(Deserved deserved) {
		this.deserved = deserved;
	}

	public List<Deserved> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<Deserved> pageItems) {
		this.pageItems = pageItems;
	}

	@Override
	protected Deserved getItem() {
		// TODO Auto-generated method stub
		return deserved;
	}

	@Override
	protected void setItem(Deserved item) {
		// TODO Auto-generated method stub
		this.deserved = item;
	}

	@Override
	protected long getId() {
		// TODO Auto-generated method stub
		return deserved.getId();
	}

	@Override
	protected ICRUDOperations<Deserved> getAbstractFacade() {
		// TODO Auto-generated method stub
		return deservedFacade;
	}

}
