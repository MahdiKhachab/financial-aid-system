package org.controller;

import java.io.UncheckedIOException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

import org.facade.DeadPersonFacade;
import org.facade.NeedDetailsFacade;
import org.model.DeadNeed;
import org.model.DeadPerson;
import org.model.NeedDetails;
import org.omnifaces.util.Ajax;
import org.omnifaces.util.Faces;

import cfg.facade.ReligionReferenceCfgFacade;
import cfg.facade.ServiceFacade;
import cfg.model.ReligionReferenceCfg;
import cfg.model.Service;
import enums.NeedTypeEnum;
import interfaces.ICRUDOperations;
import utils.CommonUtility;
import utils.Utility;

@ViewScoped
@Named
public class DeadPersonCardController extends AbstractController<DeadPerson> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private DeadPersonFacade deadPersonFacade;

	@Inject
	private ServiceFacade serviceFacade;

	@Inject
	private NeedDetailsFacade needDetailsFacade;

	@Inject
	private ReligionReferenceCfgFacade religionFacade;

	private List<Service> services = new ArrayList<Service>();

	private List<ReligionReferenceCfg> religions = new ArrayList<>();

	private DeadPerson deadPerson;

	private DeadNeed deadNeed = new DeadNeed();

	private final String REQUEST_PARAM = "id";

	@PostConstruct
	public void init() {

		services = serviceFacade.findAll();
		religions = religionFacade.findAll();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				deadPerson = find(Long.parseLong(id));
				if (deadPerson == null) {
					try {
						Faces.redirect("exception");
					} catch (UncheckedIOException e) {
						e.printStackTrace();
					}
				}
				Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
				if (flash.containsKey("new-card")) {
					Utility.addSuccessMessage("save_success");
				}
			} else {
				deadPerson = new DeadPerson();
			}
		}

	}

	private boolean showPanel = false;

	public void addPersonNeed() {
		deadNeed = new DeadNeed();
		this.deadPerson.addToDeadNeedList(deadNeed);
	}

	public void saveDialog() {

		for (DeadNeed d : deadPerson.getDeadNeedList()) {
			if (d.getService().equals(deadNeed.getService())) {
				Utility.addErrorMessage("need_type_repeated");
				return;
			}
		}

		if (deadNeed.getService().isCounted() && deadNeed.getNeedType().equals(NeedTypeEnum.NOT_SET)) {
			Utility.addErrorMessage("need_type_require_for_counted_needs");
			return;
		}

		if (deadNeed.getService().isCounted() && deadNeed.getCount() == 0) {
			Utility.addErrorMessage("count_value_gt_zero");
			return;
		}

		if (deadNeed.getAmount() == 0) {
			Utility.addErrorMessage("amount_value_gt_zero");
			return;
		}

		if (deadNeed.getService().isCounted()) {
			setUnitCountByType(deadNeed);
		} else {
			deadNeed.setCount(1);
			deadNeed.setNeedType(NeedTypeEnum.NOT_SET);
		}

		this.deadPerson.addToDeadNeedList(deadNeed);

		showPanel = false;
	}

	public void listenerSelectMenu() {

		if (!deadNeed.getService().isCounted()) {
			deadNeed.setNeedType(NeedTypeEnum.NOT_SET);
		}
	}

	public void setUnitCountByType(DeadNeed deadNeed) {

		int count = deadNeed.getCount();

		if (deadNeed.getNeedType() == NeedTypeEnum.DAYS)
			deadNeed.setUnitCount(deadNeed.getCount());

		else if (deadNeed.getNeedType() == NeedTypeEnum.MONTHS)
			deadNeed.setUnitCount(count * 30);

		else if (deadNeed.getNeedType() == NeedTypeEnum.YEARS)
			deadNeed.setUnitCount(count * 365);

	}

	public void openAddDialog() {
		deadNeed = new DeadNeed();
		showPanel = true;
		Ajax.update("form-needs");
	}

	public void cancelDialog() {
		showPanel = false;
	}

	public boolean isShowPanel() {
		return showPanel;
	}

	public void setShowPanel(boolean showPanel) {
		this.showPanel = showPanel;
	}

	public DeadNeed getDeadNeed() {
		return deadNeed;
	}

	public void setDeadNeed(DeadNeed deadNeed) {
		this.deadNeed = deadNeed;
	}

	public void removePersonNeed(DeadNeed deadNeed) {
		deadNeed.setForDelete(!deadNeed.isForDelete());
	}

	public void save() throws Exception {
		try {
			Iterator<DeadNeed> rowIteratorDeadNeed;
			rowIteratorDeadNeed = (ListIterator<DeadNeed>) deadPerson.getDeadNeedList().listIterator();
			DeadNeed t;

			while (rowIteratorDeadNeed.hasNext()) {
				t = rowIteratorDeadNeed.next();
				if (t.isForDelete()) {
					deleteServices(t);
					rowIteratorDeadNeed.remove();
				}
			}

			if (getItem().getId() <= 0) {
				super.save();
				Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
				flash.put("new-card", "true");
				changeUrl();
			} else {
				deadPerson = getAbstractFacade().save(deadPerson);

			}
			Utility.addSuccessMessage("save_success");

		} catch (Exception e) {
			CommonUtility.addMessageToFacesContext(e.getMessage(), "error_on_save");

		}
	}
	
	public void deleteServices(DeadNeed deadNeed) {
	
		List<NeedDetails> needDetails = needDetailsFacade.findByDeadNeed(deadNeed);

		Iterator<NeedDetails> rowIterator;
		rowIterator = (ListIterator<NeedDetails>) needDetails.listIterator();
		NeedDetails c;

		while (rowIterator.hasNext()) {
			c = rowIterator.next();
			try {
				needDetailsFacade.remove(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rowIterator.remove();
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
	protected DeadPerson getItem() {
		return deadPerson;
	}

	@Override
	protected void setItem(DeadPerson item) {
		this.deadPerson = item;
	}

	@Override
	protected long getId() {
		return deadPerson.getId();
	}

	@Override
	protected ICRUDOperations<DeadPerson> getAbstractFacade() {
		return deadPersonFacade;
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

	public DeadPerson getDeadPerson() {
		return deadPerson;
	}

	public void setDeadPerson(DeadPerson deadPerson) {
		this.deadPerson = deadPerson;
	}

}
