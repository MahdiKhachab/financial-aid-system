package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.DeadPersonFacade;
import org.facade.NeedDetailsFacade;
import org.model.DeadNeed;
import org.model.DeadPerson;
import org.model.NeedDetails;

import interfaces.ICRUDOperations;
import utils.Utility;

@Named
@ViewScoped
public class DeadPersonController extends AbstractController<DeadPerson> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DeadPersonFacade deadPersonFacade;
	@Inject
	private NeedDetailsFacade needDetailsFacade;

	private DeadPerson deadPerson;

	private List<DeadPerson> filteredList;
	private List<DeadPerson> pageItems = new ArrayList<DeadPerson>();

	@PostConstruct
	public void init() {
		pageItems = getAbstractFacade().findAll();
	}

	@Override
	protected DeadPerson getItem() {
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

	public void delete(DeadPerson deadPerson) {
		try {
			deleteIfRelatedWithDetails(deadPerson);
			getAbstractFacade().remove(deadPerson);
			pageItems.remove(deadPerson);
			Utility.addSuccessMessage("delete_succes");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteIfRelatedWithDetails(DeadPerson deadPerson) {

		List<DeadNeed> needs = deadPerson.getDeadNeedList();

		List<NeedDetails> needDetailsList = null;

		for (DeadNeed deadNeed : needs) {

			needDetailsList = needDetailsFacade.findByDeadNeed(deadNeed);

			if (needDetailsList != null && !needDetailsList.isEmpty()) {

				needDetailsList.stream().forEach(n -> {
					try {
						needDetailsFacade.remove(n);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

			}
		}
	}

	public int countItems() {
		return this.pageItems.size();
	}

	public void select(DeadPerson deadPerson) {
		this.deadPerson = deadPerson;
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

}
