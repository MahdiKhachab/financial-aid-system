package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.ProvideAidFacade;
import org.model.ProvideAidCompany;

import cfg.facade.CompanyCfgFacade;
import cfg.model.CompanyCfg;
import cfg.model.SubmissionDetailsCfg;
import enums.CurrencyEnum;
import utils.Utility;

@ViewScoped
@Named
public class SubbmissionAidCompanyController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	@Inject
	private ProvideAidFacade provideAidFacade;


	private List<SubmissionDetailsCfg> listSubmissionDetailsCfg = new ArrayList<>();
	private boolean showDate;

	private Date dateReceive;

	private List<ProvideAidCompany> listProvideAidCompany = new ArrayList<>();

	@Inject
	private CompanyCfgFacade companyCfgFacade;

	private List<CompanyCfg> listCompanyCfg = new ArrayList<CompanyCfg>();

	private CompanyCfg selectedCompanyCfg = new CompanyCfg();

	
	private String delegate;
	@PostConstruct
	public void init() {
		listCompanyCfg = companyCfgFacade.findAll();
		listSubmissionDetailsCfg = provideAidFacade.getAllSubmissionDetails();
		add();
	}

	public void add() {
		this.listProvideAidCompany.add(new ProvideAidCompany());
	}

	public void removeObjFromList(ProvideAidCompany provideAidRabet) {

		listProvideAidCompany.remove(provideAidRabet);
	}

	public void save() {
		try {
			for (ProvideAidCompany provideAid : listProvideAidCompany) {
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
				provideAidFacade.saveListFromDesrvedCompany(listProvideAidCompany, dateReceive, selectedCompanyCfg ,delegate);
			} else {
				provideAidFacade.saveListFromDesrvedCompany(listProvideAidCompany, new Date(), selectedCompanyCfg ,delegate);

			}

			Utility.addSuccessMessage("save_success");
			listProvideAidCompany = new ArrayList<>();
			add();
			selectedCompanyCfg = new CompanyCfg();
			delegate = "" ;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public List<ProvideAidCompany> getListProvideAidCompany() {
		return listProvideAidCompany;
	}

	public void setListProvideAidCompany(List<ProvideAidCompany> listProvideAidCompany) {
		this.listProvideAidCompany = listProvideAidCompany;
	}

	public List<CompanyCfg> getListCompanyCfg() {
		return listCompanyCfg;
	}

	public void setListCompanyCfg(List<CompanyCfg> listCompanyCfg) {
		this.listCompanyCfg = listCompanyCfg;
	}

	public CompanyCfg getSelectedCompanyCfg() {
		return selectedCompanyCfg;
	}

	public void setSelectedCompanyCfg(CompanyCfg selectedCompanyCfg) {
		this.selectedCompanyCfg = selectedCompanyCfg;
	}

	public String getDelegate() {
		return delegate;
	}

	public void setDelegate(String delegate) {
		this.delegate = delegate;
	}
	
	
	

}
