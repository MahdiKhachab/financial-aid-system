package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.NeedDetailsFacade;
import org.facade.WorkerFacade;
import org.model.Worker;
import org.omnifaces.util.Ajax;

import cfg.facade.ReligionReferenceCfgFacade;
import cfg.facade.ServiceFacade;
import cfg.model.ReligionReferenceCfg;
import cfg.model.Service;
import interfaces.ICRUDOperations;
import utils.Utility;

@Named
@ViewScoped
public class WorkerListController extends AbstractController<Worker> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private WorkerFacade workerFacade;
	@Inject
	private ReligionReferenceCfgFacade religionFacade;
	@Inject
	private ServiceFacade serviceFacade;
	@Inject
	private NeedDetailsFacade needDetailsFacade;

	private List<Worker> filteredList;

	private List<Worker> pageItems = new ArrayList<Worker>();
	private List<ReligionReferenceCfg> religionList = new ArrayList<>();
	private List<Service> serviceList = new ArrayList<>();

	private Worker worker;

	@PostConstruct
	public void init() {
		religionList = religionFacade.findAll();
		serviceList = serviceFacade.findAll();
		pageItems = getAbstractFacade().findAll();
	}

	public void checkEditable() {

	}

	public List<Service> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<Service> serviceList) {
		this.serviceList = serviceList;
	}

	public List<Worker> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<Worker> filteredList) {
		this.filteredList = filteredList;
	}

	public List<ReligionReferenceCfg> getReligionList() {
		return religionList;
	}

	public void setReligionList(List<ReligionReferenceCfg> religionList) {
		this.religionList = religionList;
	}

	public void select(Worker item) {
		this.worker = item;
	}

	public List<Worker> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<Worker> pageItems) {
		this.pageItems = pageItems;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public void delete(Worker worker) {
		try {
			if (!needDetailsFacade.findByWorker(worker))
				getAbstractFacade().remove(worker);
			else {
				Utility.addErrorMessage("worker_cant_remove_relate_with_other");
				Ajax.update(":messageScript");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public WorkerListController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Worker getItem() {
		// TODO Auto-generated method stub
		return worker;
	}

	@Override
	protected void setItem(Worker item) {
		// TODO Auto-generated method stub
		this.worker = item;
	}

	@Override
	protected long getId() {
		// TODO Auto-generated method stub
		return worker.getId();
	}

	@Override
	protected ICRUDOperations<Worker> getAbstractFacade() {
		// TODO Auto-generated method stub
		return workerFacade;
	}
	
	public int countItems() {
		return this.pageItems.size();
	}

}
