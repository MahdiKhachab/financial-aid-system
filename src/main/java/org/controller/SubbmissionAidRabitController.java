package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.facade.DeservedFacade;
import org.facade.ProvideAidFacade;
import org.model.ProvideAidRabet;

import cfg.model.SubmissionDetailsCfg;
import enums.CurrencyEnum;
import utils.Utility;

@ViewScoped
@Named
public class SubbmissionAidRabitController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private DeservedFacade deservedFacade;

	@Inject
	private ProvideAidFacade provideAidFacade;

//	private final String REQUEST_PARAM = "id";

	private List<SubmissionDetailsCfg> listSubmissionDetailsCfg = new ArrayList<>();
	private boolean showDate;

	private Date dateReceive;

	private List<ProvideAidRabet> provideAidList = new ArrayList<>();

	private String RabitName;

	private String delegate;

	@PostConstruct
	public void init() {
		add();
		listSubmissionDetailsCfg = provideAidFacade.getAllSubmissionDetails();

	}

	public void add() {
		this.provideAidList.add(new ProvideAidRabet());
	}

	public void removeObjFromList(ProvideAidRabet provideAidRabet) {

		provideAidList.remove(provideAidRabet);
	}

	public void save() {
		try {
			
			for (ProvideAidRabet provideAid : provideAidList) {
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
				provideAidFacade.saveListFromDesrvedRabit(provideAidList, dateReceive, RabitName, delegate);
			} else {
				provideAidFacade.saveListFromDesrvedRabit(provideAidList, new Date(), RabitName, delegate);

			}

			Utility.addSuccessMessage("save_success");
			provideAidList = new ArrayList<>();
			add();
			RabitName = "";
			delegate = "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DeservedFacade getDeservedFacade() {
		return deservedFacade;
	}

	public void setDeservedFacade(DeservedFacade deservedFacade) {
		this.deservedFacade = deservedFacade;
	}

	public ProvideAidFacade getProvideAidFacade() {
		return provideAidFacade;
	}

	public void setProvideAidFacade(ProvideAidFacade provideAidFacade) {
		this.provideAidFacade = provideAidFacade;
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

	public List<ProvideAidRabet> getProvideAidList() {
		return provideAidList;
	}

	public void setProvideAidList(List<ProvideAidRabet> provideAidList) {
		this.provideAidList = provideAidList;
	}

	public String getRabitName() {
		return RabitName;
	}

	public void setRabitName(String rabitName) {
		RabitName = rabitName;
	}

	public String getDelegate() {
		return delegate;
	}

	public void setDelegate(String delegate) {
		this.delegate = delegate;
	}
}
