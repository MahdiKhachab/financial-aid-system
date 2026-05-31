package org.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.facade.MediatorFacade;
import org.facade.SponsorFacade;
import org.model.Attachment;
import org.model.Mediator;
import org.model.Sponsor;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import cfg.facade.CompanyCfgFacade;
import cfg.model.CompanyCfg;
import enums.MediatorMonyEnum;
import interfaces.ICRUDOperations;
import utils.Utility;

@Named
@ViewScoped
public class MediatorCardController extends AbstractController<Mediator> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MediatorFacade mediatorFacade;
	@Inject
	private SponsorFacade sponsorFacade;
	@Inject
	private CompanyCfgFacade companyFacade;

	private final String REQUEST_PARAM = "id";

	private List<Sponsor> sponsors = new ArrayList<Sponsor>();

	private List<CompanyCfg> companies = new ArrayList<>();

	private Mediator mediator;

	private boolean selectSponsor;

	@PostConstruct
	public void init() {
		sponsors = sponsorFacade.findAll();
		companies = companyFacade.findAll();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				mediator = find(Long.parseLong(id));
				if (mediator == null) {
					try {
						Faces.redirect("exception");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					selectSponsor = mediator.getFromSponsor() != null ? true : false;
				}
				Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
				if (flash.containsKey("new-card")) {

					flash.setKeepMessages(true);
					Utility.addSuccessMessage("save_success");
				}
			} else {
				mediator = new Mediator();
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
			this.mediator.addToAttachment(attachment);
			Utility.addSuccessMessage("related_file_added");
		} catch (Exception e) {
			Utility.addErrorMessage("error_file_added");
			e.printStackTrace();
		}
	}

	public void resetReceiver() {
		this.mediator.setReceiverOfCompany("");
	}

	public boolean isSelectSponsor() {
		return selectSponsor;
	}

	public void setSelectSponsor(boolean selectSponsor) {
		this.selectSponsor = selectSponsor;
	}

	@Override
	public void save() throws Exception {

		if (getItem().isDelivered()
				&& (getItem().getAttachmentList().isEmpty() || StringUtils.isBlank(getItem().getReceiverOfCompany()))) {

			if (getItem().getAttachmentList().isEmpty()) {
				Utility.addErrorMessage("need_attachment_file");
			}
			if (StringUtils.isBlank(getItem().getReceiverOfCompany())) {
				Utility.addErrorMessage("company_receiver_name_required");
			}
			return;
		}

		if (getItem().getToCompany() == null) {
			Utility.addErrorMessage("mediator_select_company");
			return;
		}

		if (getItem().getCurrency() == null) {
			Utility.addErrorMessage("select_currency_type");
			return;
		}

		if (getItem().getMonyEnum() == null) {
			Utility.addErrorMessage("select_money_type");
			return;
		}

		if (getItem().getReceiveDate() == null) {
			Utility.addErrorMessage("receive_date_is_required");
			return;
		}

		if (getItem().getAmmount() == 0) {
			Utility.addErrorMessage("ammount_is_required");
			return;
		}

		if (getItem().getId() <= 0) {
			if (selectSponsor) {
				this.mediator.setDelegateName(this.mediator.getFromSponsor().getFullName());
				this.mediator.setPhoneNo(this.mediator.getFromSponsor().getPhoneNo());
			}
			super.save();
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.put("new-card", "true");
			changeUrl();
		} else {

			if (selectSponsor) {
				this.mediator.setDelegateName(this.mediator.getFromSponsor().getFullName());
				this.mediator.setPhoneNo(this.mediator.getFromSponsor().getPhoneNo());
			} else {
				this.mediator.setFromSponsor(null);
			}
			mediator = getAbstractFacade().save(mediator);
			Utility.addSuccessMessage("save_success");
		}
	}

	public void listenerSelectedSponsor() {
		this.mediator.setPhoneNo(this.mediator.getFromSponsor().getPhoneNo());
	}

	private void changeUrl() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		String url = request.getRequestURL().toString();
		try {
			Faces.redirect(url + "?" + REQUEST_PARAM + "=%s", getItem().getId() + "");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Sponsor> getSponsors() {
		return sponsors;
	}

	public void setSponsors(List<Sponsor> sponsors) {
		this.sponsors = sponsors;
	}

	public List<CompanyCfg> getCompanies() {
		return companies;
	}

	public void setCompanies(List<CompanyCfg> companies) {
		this.companies = companies;
	}

	public Mediator getMediator() {
		return mediator;
	}

	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}

	@Override
	protected Mediator getItem() {
		// TODO Auto-generated method stub
		return this.mediator;
	}

	@Override
	protected void setItem(Mediator item) {
		this.mediator = item;

	}

	@Override
	protected long getId() {
		// TODO Auto-generated method stub
		return this.mediator.getId();
	}

	@Override
	protected ICRUDOperations<Mediator> getAbstractFacade() {
		// TODO Auto-generated method stub
		return this.mediatorFacade;
	}

}
