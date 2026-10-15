package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.StoresFacade;
import org.model.Stores;

import interfaces.ICRUDOperations;
import utils.CommonUtility;
import utils.Utility;

@ViewScoped
@Named
public class StoresListController extends AbstractController<Stores> implements Serializable {
	private static final long serialVersionUID = 1L;
	private Stores stores;
	private List<Stores> pageItems = new ArrayList<Stores>();
	private List<Stores> filteredList;

	@Inject
	private StoresFacade StoresFacade;

	@PostConstruct
	public void init() {

		pageItems = getAbstractFacade().findAll();

	}

	public void delete(Stores stores) {
		try {
			getAbstractFacade().remove(stores);
			this.pageItems.remove(stores);
			CommonUtility.addMessageToFacesContextFromBundle("update_succes", "resources.Bundle", "success");

		} catch (Exception e) {
			Utility.addWarningMessage(e.getMessage());
		}
	}

	public int getCountItems() {
		return pageItems.size();
	}

	public Stores getStores() {
		return stores;
	}

	public void setStores(Stores stores) {
		this.stores = stores;
	}

	public List<Stores> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<Stores> pageItems) {
		this.pageItems = pageItems;
	}

	public List<Stores> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<Stores> filteredList) {
		this.filteredList = filteredList;
	}

	@Override
	protected ICRUDOperations<Stores> getAbstractFacade() {

		return StoresFacade;
	}

	@Override
	protected long getId() {

		return 0;
	}

	@Override
	protected void setItem(Stores item) {
		this.stores = item;
	}

	@Override
	protected Stores getItem() {
		// TODO Auto-generated method stub
		return stores;
	}

}
