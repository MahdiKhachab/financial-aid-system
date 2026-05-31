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

import org.facade.DeadNeedFacade;
import org.facade.NeedDetailsFacade;
import org.facade.WorkerFacade;
import org.model.Attachment;
import org.model.DeadNeed;
import org.model.NeedDetails;
import org.model.Worker;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import enums.NeedTypeEnum;
import utils.Utility;

@Named
@ViewScoped
public class NeedDetailsCardController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String REQUEST_PARAM = "id";

	@Inject
	private DeadNeedFacade deadNeedFacade;
	@Inject
	private NeedDetailsFacade needDetailsFacade;
	@Inject
	private WorkerFacade workerFacade;

	private List<Worker> workers = new ArrayList<>();
	private List<NeedDetails> needDetailsList = new ArrayList<NeedDetails>();
	private List<NeedDetails> filteredList;

	private DeadNeed deadNeed;

	private boolean showPanel = false;
	private boolean editMode = false;
	private NeedDetails needDetails;
	private NeedDetails mainNeedDetails;
	private Worker worker;

	private int newDistCount = 0;
	private int totalDays = 0;
	private double totalCost = 0;
	private double newCost = 0;

	@PostConstruct
	private void init() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				deadNeed = deadNeedFacade.find(Long.parseLong(id));
				if (deadNeed == null) {
					try {
						Faces.redirect("exception");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					workers = workerFacade.findByService(deadNeed.getService());
					needDetailsList = needDetailsFacade.findByDeadNeed(deadNeed);
				}
			}
		}

	}

	public void openAddDialog() {
		workers = workerFacade.findByService(deadNeed.getService());
		if (!deadNeed.getService().isCounted() && needDetailsList.size() > 0) {
			Utility.addErrorMessage("cant_add_more_than_one_worker_need");
			return;

		} else {

			this.needDetails = new NeedDetails();
			needDetails.setDate(new Date());
			needDetails.setDeadNeed(deadNeed);

			if (!deadNeed.getService().isCounted()) {
				needDetails.setCost(deadNeed.getAmount());
				needDetails.setCount(1);
				needDetails.setNeedType(NeedTypeEnum.NOT_SET);
			}

			showPanel = true;
			editMode = false;
		}
	}

	public void openUpdateDialog(NeedDetails needDetails) {
		worker = needDetails.getWorker();
		if (!workers.contains(worker)) {
			workers.add(worker);
		}
		mainNeedDetails = needDetails;
		try {
			this.needDetails = needDetails.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showPanel = true;
		editMode = true;

	}

	public void cancelDialog() {
		showPanel = false;
		editMode = false;
	}

	public void saveDialog() {

		if (needDetails.getCost() == 0 || needDetails.getCount() == 0) {
			Utility.addErrorMessage("count_value_gt_zero");
			return;
		}

		int count = needDetails.getCount();

		if (needDetails.getNeedType() == NeedTypeEnum.DAYS)
			needDetails.setUnitCount(count);

		else if (needDetails.getNeedType() == NeedTypeEnum.MONTHS)
			needDetails.setUnitCount(count * 30);

		else if (needDetails.getNeedType() == NeedTypeEnum.YEARS)
			needDetails.setUnitCount(count * 365);

		validateDistributedValues(needDetails);

		if (needDetails.getCost() < needDetails.getReceivedCost()) {
			Utility.addErrorMessage("cost_greater_than_need_of_worker");
			return;
		}

		// if (needDetails.getCost() < needDetails.getReceivedCost() + newCost) {
		// Utility.addErrorMessage("cost_greater_than_need_of_worker");
		// return;
		// }

		if (!editMode) {

			if (totalCost + needDetails.getCost() > deadNeed.getAmount()) {
				Utility.addErrorMessage("cost_greater_than_need");
				return;
			}

			if (totalDays + newDistCount > deadNeed.getUnitCount()) {
				Utility.addErrorMessage("count_greater_than_need");
				return;
			}

		} else {

			if (totalCost > deadNeed.getAmount()) {
				Utility.addErrorMessage("cost_greater_than_need");
				return;
			}

			if (totalDays > deadNeed.getUnitCount()) {
				Utility.addErrorMessage("count_greater_than_need");
				return;
			}
		}

		if (needDetails.getCost() < needDetails.getReceivedCost()) {
			Utility.addErrorMessage("cost_greater_than_need_of_worker");
			return;
		}

		if (!editMode) {
			this.needDetailsList.add(needDetails);

		} else {
			this.needDetailsList.set(needDetailsList.indexOf(mainNeedDetails), needDetails);
		}

		try {
			needDetails = needDetailsFacade.save(needDetails);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			if (sumTotalDays() == deadNeed.getUnitCount()) {

				if (needDetailsList.stream().anyMatch(x -> x.isFinished() == false)) {
					deadNeed.setFinished(false);
				} else {
					deadNeed.setFinished(true);
				}
				deadNeed = deadNeedFacade.save(deadNeed);

			} else {
				deadNeed.setFinished(false);
				deadNeed = deadNeedFacade.save(deadNeed);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		showPanel = false;
		Utility.addSuccessMessage("save_success");
	}

	public int sumTotalDays() {

		int sum = 0;

		for (NeedDetails ns : needDetailsList) {

			if (ns.getNeedType() == NeedTypeEnum.DAYS)
				sum += ns.getCount();

			if (ns.getNeedType() == NeedTypeEnum.MONTHS)
				sum += ns.getCount() * 30;

			if (ns.getNeedType() == NeedTypeEnum.YEARS)
				sum += ns.getCount() * 365;
		}
		return sum;

	}

	public void deleteService(NeedDetails needDetails) {
		try {
			needDetailsFacade.remove(needDetails);
			this.needDetailsList.remove(needDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void select(NeedDetails needDetails) {
		this.needDetails = needDetails;
	}

	public void validateDistributedValues(NeedDetails needDetails) {

		newDistCount = 0;// distributed count
		totalDays = 0;
		totalCost = 0;

		if (deadNeed.getService().isCounted()) {

			newDistCount = needDetails.getUnitCount();

			for (NeedDetails ns : needDetailsList) {

				if (editMode && ns.equals(mainNeedDetails)) {
					totalCost += needDetails.getCost();
					totalDays += needDetails.getUnitCount();
					continue;
				}
				totalCost += ns.getCost();
				totalDays += ns.getUnitCount();
			}
		}
	}

	public void handleOtherFileUpload(FileUploadEvent event) {
		try {
			UploadedFile uploadedFile = event.getFile();
			Attachment attachment = new Attachment();
			attachment.setFileName(uploadedFile.getFileName());
			attachment.setSize(uploadedFile.getSize());
			attachment.setContentType(uploadedFile.getContentType());
			attachment.setData(uploadedFile.getContents());
			this.needDetails.addToAttachment(attachment);
			Utility.addSuccessMessage("related_file_added");
		} catch (Exception e) {
			Utility.addErrorMessage("error_file_added");
			e.printStackTrace();
		}
	}

	public void download(Attachment attachment) {
		try {
			Faces.sendFile(attachment.getData(), attachment.getFileName(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteAttachment(Attachment attachment) {
		try {
			this.needDetails.removeFromAttachment(attachment);
			needDetails = this.needDetailsFacade.save(needDetails);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void addNewReceiveCost() {

		if (newCost == 0) {
			Utility.addErrorMessage("amount_value_gt_zero");
			return;
		}

		if (needDetails.getCost() < needDetails.getReceivedCost() + newCost) {
			Utility.addErrorMessage("cost_greater_than_need_of_worker");
			return;
		}
		this.needDetails.setReceivedCost(needDetails.getReceivedCost() + newCost);
	}

	public double getNewCost() {
		return newCost;
	}

	public void setNewCost(double newCost) {
		this.newCost = newCost;
	}

	public NeedDetails getNeedDetails() {
		return needDetails;
	}

	public void setNeedDetails(NeedDetails needDetails) {
		this.needDetails = needDetails;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<NeedDetails> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<NeedDetails> filteredList) {
		this.filteredList = filteredList;
	}

	public boolean isShowPanel() {
		return showPanel;
	}

	public void setShowPanel(boolean showPanel) {
		this.showPanel = showPanel;
	}

	public List<Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}

	public DeadNeed getDeadNeed() {
		return deadNeed;
	}

	public void setDeadNeed(DeadNeed deadNeed) {
		this.deadNeed = deadNeed;
	}

	public List<NeedDetails> getNeedDetailsList() {
		return needDetailsList;
	}

	public void setNeedDetailsList(List<NeedDetails> needDetailsList) {
		this.needDetailsList = needDetailsList;
	}

}
