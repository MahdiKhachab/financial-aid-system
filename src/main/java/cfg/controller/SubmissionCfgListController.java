package cfg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.controller.AbstractController;
import org.omnifaces.util.Ajax;

import cfg.facade.SubmissionCfgFacade;
import cfg.model.SubmissionCfg;
import cfg.model.SubmissionDetailsCfg;
import enums.SubmissionTabViewEnum;
import interfaces.ICRUDOperations;
import utils.Utility;

@Named
@ViewScoped
public class SubmissionCfgListController extends AbstractController<SubmissionCfg> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private SubmissionCfgFacade submissionCfgFacade;

	private SubmissionCfg submissionCfg;
	private List<SubmissionCfg> pageItems = new ArrayList<SubmissionCfg>();
	private List<SubmissionCfg> filteredList;

	private SubmissionTabViewEnum tabView = SubmissionTabViewEnum.DETAILSTABVIEW;

	private double totalValueLL;
	private double totalValueDD;

	@PostConstruct
	public void init() {
		pageItems = getAbstractFacade().findAll();

		pageItems.stream().forEach(

				(x -> {
					for (SubmissionDetailsCfg var : x.getSubmissionDetailsList()) {
						totalValueLL += var.getBalance_ll();
					}
				}));

		pageItems.stream().forEach((x -> {
			for (SubmissionDetailsCfg var : x.getSubmissionDetailsList()) {
				totalValueDD += var.getBalance_dd();
			}
		}));

	}

	public SubmissionTabViewEnum getTabView() {
		return tabView;
	}

	public void setTabView(SubmissionTabViewEnum tabView) {
		this.tabView = tabView;
	}

	public void changeTabView(SubmissionTabViewEnum tabView) {
		this.tabView = tabView;
	}

	public int getCountItems() {
		return pageItems.size();
	}

	public void select(SubmissionCfg item) {
		this.submissionCfg = item;
	}

	public void delete(SubmissionCfg submissionCfg) {
		try {

			if (submissionCfg.getSubmissionDetailsList() != null)
				submissionCfg.setSubmissionDetailsList(null);

			getAbstractFacade().remove(submissionCfg);
			pageItems = getAbstractFacade().findAll();
			Utility.addSuccessMessage("remove_success");
			Ajax.update("formTemplate:messageScript");
		} catch (Exception e) {
			Utility.addErrorMessage("submission_rows_related_with_sponsers");
			Utility.addWarningMessage(e.getMessage());
		}
	}

	public List<SubmissionCfg> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<SubmissionCfg> filteredList) {
		this.filteredList = filteredList;
	}

	public SubmissionCfg getSubmissionCfg() {
		return submissionCfg;
	}

	public void setSubmissionCfg(SubmissionCfg submissionCfg) {
		this.submissionCfg = submissionCfg;
	}

	public List<SubmissionCfg> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<SubmissionCfg> pageItems) {
		this.pageItems = pageItems;
	}

	@Override
	protected SubmissionCfg getItem() {
		// TODO Auto-generated method stub
		return submissionCfg;
	}

	@Override
	protected void setItem(SubmissionCfg item) {
		// TODO Auto-generated method stub
		this.submissionCfg = item;
	}

	@Override
	protected long getId() {
		// TODO Auto-generated method stub
		return submissionCfg.getId();
	}

	@Override
	protected ICRUDOperations<SubmissionCfg> getAbstractFacade() {
		// TODO Auto-generated method stub
		return submissionCfgFacade;
	}

	public double getTotalValueLL() {
		return totalValueLL;
	}

	public void setTotalValueLL(double totalValueLL) {
		this.totalValueLL = totalValueLL;
	}

	public double getTotalValueDD() {
		return totalValueDD;
	}

	public void setTotalValueDD(double totalValueDD) {
		this.totalValueDD = totalValueDD;
	}

}
