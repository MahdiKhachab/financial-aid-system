package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.facade.DeservedFacade;
import org.model.Deserved;

import cfg.facade.HealthyStatusCfgFacade;
import cfg.facade.InssuranceCfgFacade;
import cfg.facade.PoorPercenatageCfgFacade;
import cfg.facade.ReligionReferenceCfgFacade;
import cfg.model.HealthyStatusCfg;
import cfg.model.InssuranceCfg;
import cfg.model.PoorPercentageCfg;
import cfg.model.ReligionReferenceCfg;
import utils.Utility;

@ViewScoped
@Named
public class SubbmissionWithConditionAidController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DeservedFacade deservedFacade;

	@Inject
	private HealthyStatusCfgFacade healthyStatusCfgFacade;

	@Inject
	private PoorPercenatageCfgFacade poorPercenatageCfgFacade;

	@Inject
	private ReligionReferenceCfgFacade religionReferenceCfgFacade;

	@Inject
	private InssuranceCfgFacade inssuranceCfgFacade;

	private List<HealthyStatusCfg> healthyStatusList = new ArrayList<>();
	private List<InssuranceCfg> inssuranceCfgList = new ArrayList<>();
	private List<ReligionReferenceCfg> religionReferenceCfgList = new ArrayList<>();
	private List<PoorPercentageCfg> poorPercentageCfgList = new ArrayList<>();

	private boolean hasCar = false;
	private boolean 	 richLogical  = false;
	private int nbChildFrom;
	private int nbChildTo;

	private HealthyStatusCfg healthyStatusCfg;
	private PoorPercentageCfg poorPercenatageCfg;
	private ReligionReferenceCfg religionReferenceCfg;
	private InssuranceCfg inssuranceCfg;

	// listResult ;
	private List<Deserved> pageItems = new ArrayList<>();
	private List<Deserved> filteredList;
	
	
	private String livingArea = null  ;

	@PostConstruct
	public void init() {

		healthyStatusList = healthyStatusCfgFacade.findAll();
		inssuranceCfgList = inssuranceCfgFacade.findAll();
		religionReferenceCfgList = religionReferenceCfgFacade.findAll();
		poorPercentageCfgList = poorPercenatageCfgFacade.findAll();
		;
	}

	public void search() {

		try {

			if (nbChildFrom > nbChildTo) {

				Utility.addErrorMessage("waring_nb_frmo_bigger_nb_to");

			} else {

				pageItems = deservedFacade.findDesrvedsByList(hasCar, nbChildFrom, nbChildTo, healthyStatusCfg,
						poorPercenatageCfg, religionReferenceCfg, inssuranceCfg , richLogical ,livingArea);

			}

		} catch (Exception e) {
			e.printStackTrace();

			Utility.addErrorMessage("error_on_search");
		}

	}

	/*
	 * Getter And Setter
	 */

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

	public boolean isHasCar() {
		return hasCar;
	}

	public void setHasCar(boolean hasCar) {
		this.hasCar = hasCar;
	}

	public int getNbChildFrom() {
		return nbChildFrom;
	}

	public void setNbChildFrom(int nbChildFrom) {
		this.nbChildFrom = nbChildFrom;
	}

	public int getNbChildTo() {
		return nbChildTo;
	}

	public void setNbChildTo(int nbChildTo) {
		this.nbChildTo = nbChildTo;
	}

	public List<Deserved> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<Deserved> pageItems) {
		this.pageItems = pageItems;
	}

	public List<Deserved> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<Deserved> filteredList) {
		this.filteredList = filteredList;
	}

	public HealthyStatusCfg getHealthyStatusCfg() {
		return healthyStatusCfg;
	}

	public void setHealthyStatusCfg(HealthyStatusCfg healthyStatusCfg) {
		this.healthyStatusCfg = healthyStatusCfg;
	}

	public PoorPercentageCfg getPoorPercenatageCfg() {
		return poorPercenatageCfg;
	}

	public void setPoorPercenatageCfg(PoorPercentageCfg poorPercenatageCfg) {
		this.poorPercenatageCfg = poorPercenatageCfg;
	}

	public ReligionReferenceCfg getReligionReferenceCfg() {
		return religionReferenceCfg;
	}

	public void setReligionReferenceCfg(ReligionReferenceCfg religionReferenceCfg) {
		this.religionReferenceCfg = religionReferenceCfg;
	}

	public InssuranceCfg getInssuranceCfg() {
		return inssuranceCfg;
	}

	public void setInssuranceCfg(InssuranceCfg inssuranceCfg) {
		this.inssuranceCfg = inssuranceCfg;
	}

	public List<PoorPercentageCfg> getPoorPercentageCfgList() {
		return poorPercentageCfgList;
	}

	public void setPoorPercentageCfgList(List<PoorPercentageCfg> poorPercentageCfgList) {
		this.poorPercentageCfgList = poorPercentageCfgList;
	}

	public boolean isRichLogical() {
		return richLogical;
	}

	public void setRichLogical(boolean richLogical) {
		this.richLogical = richLogical;
	}

	public String getLivingArea() {
		return livingArea;
	}

	public void setLivingArea(String livingArea) {
		this.livingArea = livingArea;
	}
	
	

}
