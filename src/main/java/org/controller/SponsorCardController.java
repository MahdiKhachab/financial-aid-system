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

import org.apache.commons.lang3.StringUtils;
import org.facade.SponsorFacade;
import org.model.Deserved;
import org.model.Sponsor;
import org.omnifaces.util.Ajax;
import org.omnifaces.util.Faces;
import org.primefaces.context.PrimeFacesContext;

import cfg.facade.ReligionReferenceCfgFacade;
import cfg.model.ReligionReferenceCfg;
import interfaces.ICRUDOperations;
import utils.CommonUtility;
import utils.Utility;

@ViewScoped
@Named
public class SponsorCardController extends AbstractController<Sponsor> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ReligionReferenceCfgFacade religionReferenceCfgFacade;

	@Inject
	SponsorFacade sponsorFacade;

	private List<ReligionReferenceCfg> religionReferenceCfgList = new ArrayList<>();

	private Sponsor sponsor;
	private final String REQUEST_PARAM = "id";

	@PostConstruct
	public void init() {
		religionReferenceCfgList = religionReferenceCfgFacade.findAll();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				sponsor = find(Long.parseLong(id));
				if (sponsor == null) {
					try {
						Faces.redirect("exception");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
				if (flash.containsKey("new-card")) {
					Utility.addSuccessMessage("save_success");
				}
			} else {
				sponsor = new Sponsor();
			}
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
	public void save() throws Exception {
		try {

			if (StringUtils.isBlank(getItem().getPhoneNo())) {
				Utility.addErrorMessage("phone_no_is_req");
				return;
			}

			if (getItem().getId() <= 0) {
				super.save();
				Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
				flash.put("new-card", "true");
				changeUrl();
			} else {
				sponsor = getAbstractFacade().save(sponsor);

			}
			Utility.addSuccessMessage("save_success");

		} catch (Exception e) {
			// TODO: handle exception
			CommonUtility.addMessageToFacesContext(e.getMessage(), "error");

		}

	}

	public List<ReligionReferenceCfg> getReligionReferenceCfgList() {
		return religionReferenceCfgList;
	}

	public void setReligionReferenceCfgList(List<ReligionReferenceCfg> religionReferenceCfgList) {
		this.religionReferenceCfgList = religionReferenceCfgList;
	}

	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	@Override
	protected Sponsor getItem() {

		return sponsor;
	}

	@Override
	protected void setItem(Sponsor item) {
		this.sponsor = item;
	}

	@Override
	protected long getId() {

		return 0;
	}

	@Override
	protected ICRUDOperations<Sponsor> getAbstractFacade() {

		return sponsorFacade;
	}

}
