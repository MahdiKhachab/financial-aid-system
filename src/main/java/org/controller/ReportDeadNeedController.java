package org.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.DeadPersonFacade;
import org.facade.NeedDetailsFacade;
import org.model.Attachment;
import org.model.DeadNeed;
import org.model.DeadPerson;
import org.model.NeedDetails;
import org.omnifaces.util.Faces;

import utils.Utility;

@Named
@ViewScoped
public class ReportDeadNeedController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private DeadPersonFacade personFacade;

	@Inject
	private NeedDetailsFacade needDetailsFacade;

	private List<DeadPerson> deadPersonList = new ArrayList<>();

	private DeadPerson person;

	private DeadNeed deadNeed;

	private boolean finished;

	private List<NeedDetails> needDetailsList = new ArrayList<>();

	private NeedDetails needDetails;

	@PostConstruct
	public void init() {
		deadPersonList = personFacade.findAll();
	}

	public void select(NeedDetails needDetails) {
		this.needDetails = needDetails;
	}

	public List<DeadPerson> getDeadPersonList() {
		return deadPersonList;
	}

	public void setDeadPersonList(List<DeadPerson> deadPersonList) {
		this.deadPersonList = deadPersonList;
	}

	public DeadPersonFacade getPersonFacade() {
		return personFacade;
	}

	public void setPersonFacade(DeadPersonFacade personFacade) {
		this.personFacade = personFacade;
	}

	public DeadPerson getPerson() {
		return person;
	}

	public void setPerson(DeadPerson person) {
		this.person = person;
	}

	public DeadNeed getDeadNeed() {
		return deadNeed;
	}

	public void setDeadNeed(DeadNeed deadNeed) {
		this.deadNeed = deadNeed;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public List<NeedDetails> getNeedDetailsList() {
		return needDetailsList;
	}

	public void setNeedDetailsList(List<NeedDetails> needDetailsList) {
		this.needDetailsList = needDetailsList;
	}

	double restAmount = 0;

	public void search() {
		try {
			needDetailsList = needDetailsFacade.findByDeadNeedWithConditions(deadNeed, finished);
			restAmount = deadNeed.getAmount() - needDetailsList.stream().mapToDouble(x -> x.getReceivedCost()).sum();
			Utility.addSuccessMessage("search_completed");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public double getRestAmount() {
		return restAmount;
	}

	public void setRestAmount(double restAmount) {
		this.restAmount = restAmount;
	}

	public NeedDetails getNeedDetails() {
		return needDetails;
	}

	public void setNeedDetails(NeedDetails needDetails) {
		this.needDetails = needDetails;
	}

	public void download(Attachment attachment) {
		try {
			Faces.sendFile(attachment.getData(), attachment.getFileName(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
