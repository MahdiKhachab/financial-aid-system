package org.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.ReceiveMoneyFacade;
import org.facade.SponsorFacade;
import org.model.ReceiveMoney;
import org.model.Sponsor;
import org.omnifaces.util.Faces;

import cfg.model.SubmissionDetailsCfg;
import interfaces.ICRUDOperations;
import utils.Utility;

@ViewScoped
@Named
public class ReceiveMoneyController extends AbstractController<ReceiveMoney> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ReceiveMoneyFacade receiveMoneyFacade;

	@Inject
	private SponsorFacade sponsorFacade;

	private ReceiveMoney receiveMoney;

	private List<SubmissionDetailsCfg> listSubmissionDetailsCfg = new ArrayList<>();

	private List<ReceiveMoney> listReceiveMoney = new ArrayList<>();

	private Sponsor sponsor;
	private final String REQUEST_PARAM = "id";

	private boolean showDate;

	private Date dateReceive;

	@PostConstruct
	public void init() {
		listSubmissionDetailsCfg = receiveMoneyFacade.getAllSubmissionDetails();
		add();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				sponsor = sponsorFacade.find(Long.parseLong(id));
				if (sponsor == null) {
					try {
						Faces.redirect("exception");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}

	}

	public void add() {
		this.listReceiveMoney.add(new ReceiveMoney());

	}

	public void removeReceiveMoneyFromList(ReceiveMoney receiveMoney) {

		listReceiveMoney.remove(receiveMoney);
	}

	public void save() {
		try {
			
		

			if (showDate && dateReceive != null) {
				receiveMoneyFacade.saveList(listReceiveMoney, dateReceive , sponsor);
			} else {
				receiveMoneyFacade.saveList(listReceiveMoney, new Date() , sponsor);

			}

			Utility.addSuccessMessage("save_success");
			listReceiveMoney = new ArrayList<>();
			add();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	@Override
	protected ReceiveMoney getItem() {
		return receiveMoney;
	}

	@Override
	protected void setItem(ReceiveMoney item) {

		this.receiveMoney = item;

	}

	@Override
	protected long getId() {
		return 0;
	}

	public ReceiveMoney getReceiveMoney() {
		return receiveMoney;
	}

	public void setReceiveMoney(ReceiveMoney receiveMoney) {
		this.receiveMoney = receiveMoney;
	}

	public List<SubmissionDetailsCfg> getListSubmissionDetailsCfg() {
		return listSubmissionDetailsCfg;
	}

	public void setListSubmissionDetailsCfg(List<SubmissionDetailsCfg> listSubmissionDetailsCfg) {
		this.listSubmissionDetailsCfg = listSubmissionDetailsCfg;
	}

	public List<ReceiveMoney> getListReceiveMoney() {
		return listReceiveMoney;
	}

	public void setListReceiveMoney(List<ReceiveMoney> listReceiveMoney) {
		this.listReceiveMoney = listReceiveMoney;
	}

	@Override
	protected ICRUDOperations<ReceiveMoney> getAbstractFacade() {
		return receiveMoneyFacade;
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

	public void changeDate() {
		
		if(showDate) {
			showDate = false ;
		}else {
			showDate =true ;
		}
		
	}
	
}
