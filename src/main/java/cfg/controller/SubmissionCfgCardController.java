package cfg.controller;

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

import org.controller.AbstractController;
import org.omnifaces.util.Faces;

import cfg.facade.SubmissionCfgFacade;
import cfg.model.SubmissionCfg;
import cfg.model.SubmissionDetailsCfg;
import interfaces.ICRUDOperations;
import utils.Utility;

@Named
@ViewScoped
public class SubmissionCfgCardController extends AbstractController<SubmissionCfg> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private SubmissionCfgFacade submissionCfgFacade;

	private SubmissionCfg submissionCfg = new SubmissionCfg();

	private SubmissionDetailsCfg submissionDetailsCfg;

	private final String REQUEST_PARAM = "id";

	private List<SubmissionCfg> pageItems = new ArrayList<SubmissionCfg>();

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				submissionCfg = find(Long.parseLong(id));
				if (submissionCfg == null) {
					try {
						Faces.redirect("entity-not-found.xhtml");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
				if (flash.containsKey("new-card")) {
					flash.setKeepMessages(true);
					Utility.addSuccessMessage("save_success");
				}
			} else {
				submissionCfg = new SubmissionCfg();
			}
		}
	}

	public SubmissionCfg getSubmissionCfg() {
		return submissionCfg;
	}

	public void setSubmissionCfg(SubmissionCfg submissionCfg) {
		this.submissionCfg = submissionCfg;
	}

	public SubmissionDetailsCfg getSubmissionDetailsCfg() {
		return submissionDetailsCfg;
	}

	public void setSubmissionDetailsCfg(SubmissionDetailsCfg submissionDetailsCfg) {
		this.submissionDetailsCfg = submissionDetailsCfg;
	}

	public List<SubmissionCfg> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<SubmissionCfg> pageItems) {
		this.pageItems = pageItems;
	}

	@Override
	public void save() throws Exception {
		try {
			if (submissionCfg.getName() == null || submissionCfg.getName().equals("")) {
				Utility.addWarningMessage("submission_name_empty");
				return;
			}
			if (submissionCfg.getSubmissionDetailsList().size() > 0) {
				for (SubmissionDetailsCfg wv : submissionCfg.getSubmissionDetailsList()) {
					if (wv.getType() == null) {
						Utility.addWarningMessage("table_has_empty_value");
						return;
					}
				}
			}

			if (getItem().getId() <= 0) {
				super.save();
				Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
				flash.put("new-card", "true");
				changeUrl();
				Utility.addSuccessMessage("save_success");

			} else {
				try {
					submissionCfg = getAbstractFacade().save(submissionCfg);
					Utility.addSuccessMessage("save_success");
				} catch (Exception e) {
					Utility.addErrorMessage("submission_rows_related_with_sponsers");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public void addChild() {
		submissionCfg.addToSubmissionDetails(new SubmissionDetailsCfg());
	}

	public void deleteSubmissionDetailsCfg(SubmissionDetailsCfg cfg) {
		submissionCfg.removeFromSubmissionDetails(cfg);
		// Ajax.oncomplete("PF('submissionDetailsWgVar').filter();");
	}

	public void selectSubmissionDetails(SubmissionDetailsCfg cfg) {
		this.submissionDetailsCfg = cfg;
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

}
