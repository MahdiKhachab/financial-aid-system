package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.facade.DeadPersonFacade;
import org.model.DeadNeed;
import org.model.DeadPerson;
import org.omnifaces.util.Ajax;

import interfaces.ICRUDOperations;

@Named
@ViewScoped
public class NeedDetailsListController extends AbstractController<DeadPerson> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DeadPersonFacade deadPersonFacade;

	private DeadPerson deadPerson;

	private List<DeadPerson> filteredList;
	private List<DeadPerson> pageItems = new ArrayList<DeadPerson>();

	private List<DeadNeed> deadNeedList = new ArrayList<DeadNeed>();

	@PostConstruct
	public void init() {
		pageItems = getAbstractFacade().findAll();
	}

	@Override
	public DeadPerson getItem() {
		return deadPerson;
	}

	@Override
	protected void setItem(DeadPerson item) {
		this.deadPerson = item;
	}

	@Override
	protected long getId() {
		// TODO Auto-generated method stub
		return deadPerson.getId();
	}

	@Override
	protected ICRUDOperations<DeadPerson> getAbstractFacade() {
		// TODO Auto-generated method stub
		return deadPersonFacade;
	}

	public int countItems() {
		return this.pageItems.size();
	}

	public void select(DeadPerson deadPerson) {
		deadNeedList = deadPerson.getDeadNeedList();
		Ajax.oncomplete("$('#selectionNeedBtnId').click();");
		Ajax.update(":panelSelectionNeedId");
	}

	public DeadPerson getDeadPerson() {
		return deadPerson;
	}

	public void setDeadPerson(DeadPerson deadPerson) {
		this.deadPerson = deadPerson;
	}

	public List<DeadPerson> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<DeadPerson> filteredList) {
		this.filteredList = filteredList;
	}

	public List<DeadPerson> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<DeadPerson> pageItems) {
		this.pageItems = pageItems;
	}

	public List<DeadNeed> getDeadNeedList() {
		return deadNeedList;
	}

	public void setDeadNeedList(List<DeadNeed> deadNeedList) {
		this.deadNeedList = deadNeedList;
	}

}
