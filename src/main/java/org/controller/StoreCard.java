package org.controller;

import java.io.UncheckedIOException;

import java.io.IOException;
import java.io.Serializable;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

import org.facade.StoresFacade;
import org.model.Stores;
import org.omnifaces.util.Faces;

import interfaces.ICRUDOperations;
import utils.CommonUtility;
import utils.Utility;

@ViewScoped
@Named
public class StoreCard extends AbstractController<Stores> implements Serializable {
	private static final long serialVersionUID = 1L;
	private Stores stores = new Stores();

	@Inject
	private StoresFacade StoresFacade;

	private final String REQUEST_PARAM = "id";

	@PostConstruct
	public void init() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				stores = find(Long.parseLong(id));
				if (stores == null) {
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
				stores = new Stores();
			}
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
	public void save() throws Exception {
		try {
			if (getItem().getId() <= 0) {
				super.save();
				Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
				flash.put("new-card", "true");
				changeUrl();
			} else {
				stores = getAbstractFacade().save(stores);

			}
			Utility.addSuccessMessage("save_success");

		} catch (Exception e) {
			// TODO: handle exception
			CommonUtility.addMessageToFacesContext(e.getMessage(), "error");

		}

	}

	public Stores getStores() {
		return stores;
	}

	public void setStores(Stores stores) {
		this.stores = stores;
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
