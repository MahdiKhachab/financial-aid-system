package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.dtos.RemainingNeedsDto;
import org.facade.DeadNeedFacade;
import org.facade.NeedDetailsFacade;
import org.model.DeadNeed;

import cfg.facade.ServiceFacade;
import cfg.model.Service;
import utils.Utility;

@Named
@ViewScoped
public class ReportRemainingNeedsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Service service;

	private List<Service> serviceList = new ArrayList<Service>();

	private List<DeadNeed> deadNeedList = new ArrayList<>();

	private List<RemainingNeedsDto> remainingNeedsList;

	@Inject
	private DeadNeedFacade deadNeedFacade;

	@Inject
	private ServiceFacade serviceFacade;

	@Inject
	private NeedDetailsFacade needDetailsFacade;

	@PostConstruct
	public void init() {
		serviceList = serviceFacade.findAll();
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public List<Service> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<Service> serviceList) {
		this.serviceList = serviceList;
	}

	public void search() {
		try {
			int sum = 0;
			remainingNeedsList = new ArrayList<>();
			
			deadNeedList = deadNeedFacade.findByService(service);

			RemainingNeedsDto remainingNeedsDto;

			for (DeadNeed deadNeed : deadNeedList) {

				sum = needDetailsFacade.findByDeadNeed(deadNeed).stream().mapToInt(x -> x.getUnitCount()).sum();

				remainingNeedsDto = new RemainingNeedsDto();

				remainingNeedsDto.setDeadNeed(deadNeed);
				remainingNeedsDto.setDistributedValue(sum);
				remainingNeedsDto.setRestValue(deadNeed.getUnitCount() - sum);

				remainingNeedsList.add(remainingNeedsDto);
			}

			Utility.addSuccessMessage("search_completed");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public List<RemainingNeedsDto> getRemainingNeedsList() {
		return remainingNeedsList;
	}

	public void setRemainingNeedsList(List<RemainingNeedsDto> remainingNeedsList) {
		this.remainingNeedsList = remainingNeedsList;
	}

	public List<DeadNeed> getDeadNeedList() {
		return deadNeedList;
	}

	public void setDeadNeedList(List<DeadNeed> deadNeedList) {
		this.deadNeedList = deadNeedList;
	}

}
