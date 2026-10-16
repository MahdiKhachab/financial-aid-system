package org.controller;

import java.io.UncheckedIOException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

import org.facade.NeedDetailsFacade;
import org.facade.WorkerFacade;
import org.model.Worker;
import org.omnifaces.util.Faces;

import cfg.facade.ReligionReferenceCfgFacade;
import cfg.facade.ServiceFacade;
import cfg.model.ReligionReferenceCfg;
import cfg.model.Service;
import interfaces.ICRUDOperations;
import utils.CommonUtility;
import utils.Utility;

@Named
@ViewScoped
public class WorkerCardController extends AbstractController<Worker> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private WorkerFacade workerFacade;
	@Inject
	private ServiceFacade serviceFacade;
	@Inject
	private ReligionReferenceCfgFacade religionFacade;

	private List<Service> services = new ArrayList<Service>();

	private List<ReligionReferenceCfg> religions = new ArrayList<>();

	private Worker worker;

	private final String REQUEST_PARAM = "id";

	@Inject
	private NeedDetailsFacade needDetailsFacade;

	@PostConstruct
	public void init() {

		services = serviceFacade.findAll();
		religions = religionFacade.findAll();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				worker = find(Long.parseLong(id));
				if (worker == null) {
					try {
						Faces.redirect("exception");
					} catch (UncheckedIOException e) {
						e.printStackTrace();
					}
				}
				Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
				if (flash.containsKey("new-card")) {
					Utility.addSuccessMessage("save_success");
				} else {
					if (needDetailsFacade.findByWorker(worker)) {
						worker.setEditOrDelete(false);
					}
				}
			} else {
				worker = new Worker();
			}
		}

	}

	public void save() throws Exception {
		try {
			if (getItem().getId() <= 0) {
				super.save();
				Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
				flash.put("new-card", "true");
				changeUrl();
			} else {
				worker = getAbstractFacade().save(worker);

			}
			Utility.addSuccessMessage("save_success");

		} catch (Exception e) {
			CommonUtility.addMessageToFacesContext(e.getMessage(), "error_on_save");

		}
	}

	private void changeUrl() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		String url = request.getRequestURL().toString();
		try {
			Faces.redirect(url + "?" + REQUEST_PARAM + "=%s", getItem().getId() + "");
		} catch (UncheckedIOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Worker getItem() {
		return worker;
	}

	@Override
	protected void setItem(Worker item) {
		this.worker = item;
	}

	@Override
	protected long getId() {
		return worker.getId();
	}

	@Override
	protected ICRUDOperations<Worker> getAbstractFacade() {
		return workerFacade;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public List<ReligionReferenceCfg> getReligions() {
		return religions;
	}

	public void setReligions(List<ReligionReferenceCfg> religions) {
		this.religions = religions;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

}
