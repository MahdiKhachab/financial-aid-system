package org.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.facade.DeservedFacade;
import org.model.Deserved;
import org.omnifaces.util.Faces;

import cfg.facade.HealthyStatusCfgFacade;
import cfg.facade.InssuranceCfgFacade;
import cfg.facade.PoorPercenatageCfgFacade;
import cfg.facade.ReligionReferenceCfgFacade;
import cfg.model.HealthyStatusCfg;
import cfg.model.InssuranceCfg;
import cfg.model.PoorPercentageCfg;
import cfg.model.ReligionReferenceCfg;
import interfaces.ICRUDOperations;
import utils.Utility;

@ViewScoped
@Named
public class DeservedController extends AbstractController<Deserved> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DeservedFacade deservedFacade;

	@Inject
	private HealthyStatusCfgFacade healthyStatusCfgFacade;

	@Inject
	private 	PoorPercenatageCfgFacade poorPercenatageCfgFacade;

	@Inject
	private ReligionReferenceCfgFacade religionReferenceCfgFacade;

	@Inject
	private	InssuranceCfgFacade inssuranceCfgFacade;

	private Deserved deserved = new Deserved();
	private final String REQUEST_PARAM = "id";

	private List<HealthyStatusCfg> healthyStatusList = new ArrayList<>();
	private List<InssuranceCfg> inssuranceCfgList = new ArrayList<>();
	private List<ReligionReferenceCfg> religionReferenceCfgList = new ArrayList<>();
	private List<PoorPercentageCfg> poorPercentageCfgList = new ArrayList<>();

	@PostConstruct
	public void init() {
		healthyStatusList = healthyStatusCfgFacade.findAll();
		inssuranceCfgList = inssuranceCfgFacade.findAll();
		religionReferenceCfgList = religionReferenceCfgFacade.findAll();
		poorPercentageCfgList = poorPercenatageCfgFacade.findAll();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				deserved = find(Long.parseLong(id));
				if (deserved == null) {
					try {
						Faces.redirect("exception");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
				if (flash.containsKey("new-card")) {

					flash.setKeepMessages(true);
					Utility.addSuccessMessage("save_success");
				}
			} else {
				deserved = new Deserved();
			}
		}
	}

	@Override
	public void save() throws Exception {
		if (getItem().getId() <= 0) {
			super.save();
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.put("new-card", "true");
			changeUrl();
		} else {
			deserved = getAbstractFacade().save(deserved);
			Utility.addSuccessMessage("save_success");
		}
	}

	private void changeUrl() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		String url = request.getRequestURL().toString();
		try {
			Faces.redirect(url + "?" + REQUEST_PARAM + "=%s", getItem().getId() + "");

		} catch (IOException e) {
			e.printStackTrace();
		}
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
		return this.deserved.getId();
	}

	@Override
	protected ICRUDOperations<Deserved> getAbstractFacade() {
		// TODO Auto-generated method stub
		return deservedFacade;
	}

	public Deserved getDeserved() {
		return deserved;
	}

	public void setDeserved(Deserved deserved) {
		this.deserved = deserved;
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

}
