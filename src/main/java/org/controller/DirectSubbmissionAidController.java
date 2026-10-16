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
public class DirectSubbmissionAidController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DeservedFacade deservedFacade;

	@Inject
	private ProvideAidFacade provideAidFacade;

	private Deserved deserved;
	private final String REQUEST_PARAM = "id";

	private List<SubmissionDetailsCfg> listSubmissionDetailsCfg = new ArrayList<>();
	private boolean showDate;

	private Date dateReceive;

	private List<ProvideAidDesrvedPerson> provideAidList = new ArrayList<>();

	@PostConstruct
	public void init() {
		listSubmissionDetailsCfg = provideAidFacade.getAllSubmissionDetails();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				deserved = deservedFacade.find(Long.parseLong(id));
				add();
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

	public void add() {
		this.provideAidList.add(new ProvideAidDesrvedPerson());
	}

	public void removeObjFromList(ProvideAidDesrvedPerson provideAidDesrvedPerson) {

		provideAidList.remove(provideAidDesrvedPerson);
	}

	public void save() {
		try {
			for (ProvideAidDesrvedPerson provideAid : provideAidList) {
				if (provideAid.getCurrencyEnum() == CurrencyEnum.Lira) {

					if(  Double.compare( provideAid.getValue(), provideAid.getSubmissionDetailsCfg().getBalance_ll()) >0  ) {
						Utility.addWarningMessage(  Utility.getMessage("You_do_not_have _this_balance to_submit",  Utility.BUNDLE_FILE_NAME)   + " " +  provideAid.getSubmissionDetailsCfg().getType());
						return ;
					}
						
				} else {
					
					if( Double.compare( provideAid.getValue(), provideAid.getSubmissionDetailsCfg().getBalance_dd()) >0  ) {
						Utility.addWarningMessage(  Utility.getMessage("You_do_not_have _this_balance to_submit", Utility.BUNDLE_FILE_NAME)  +  " " + provideAid.getSubmissionDetailsCfg().getType());
						return ;
						
				}

			}
			}

			if (showDate && dateReceive != null) {
				provideAidFacade.saveListFromDesrvedPerson(provideAidList, dateReceive, deserved);
			} else {
				provideAidFacade.saveListFromDesrvedPerson(provideAidList, new Date(), deserved);

			}

			Utility.addSuccessMessage("save_success");
			provideAidList = new ArrayList<>();
			add();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public boolean isShowDate() {
		return showDate;
	}

	public void setShowDate(boolean showDate) {
		this.showDate = showDate;
	}

	public Date getDateReceive() {
		return dateReceive;
	}

	public void setDateReceive(Date dateReceive) {
		this.dateReceive = dateReceive;
	}

	public List<ProvideAidDesrvedPerson> getProvideAidList() {
		return provideAidList;
	}

	public void setProvideAidList(List<ProvideAidDesrvedPerson> provideAidList) {
		this.provideAidList = provideAidList;
	}

}
