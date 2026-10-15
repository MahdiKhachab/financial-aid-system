package org.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.SubbmissionExchnageFacade;
import org.model.SubbmissionExchnage;

import cfg.facade.SubmissionDetailsCfgFacade;
import cfg.model.SubmissionDetailsCfg;
import utils.CommonUtility;

@Named
@ViewScoped
public class SubbmissionExchnageController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private SubbmissionExchnageFacade subbmissionExchnageFacade;;

	private SubmissionDetailsCfg submissionDetailsCfg;

	@Inject
	private SubmissionDetailsCfgFacade submissionDetailsCfgFacade;

	private String REQUEST_PARAM = "id";

	private SubbmissionExchnage subbmissionExchnage = new SubbmissionExchnage();

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				submissionDetailsCfg = submissionDetailsCfgFacade.find(Long.parseLong(id));
				subbmissionExchnage = new SubbmissionExchnage();
			}
		}

	}

	public void submit() {

		try {

			if (subbmissionExchnage.getDollarsValue() > submissionDetailsCfg.getBalance_dd()) {
				CommonUtility.addMessageToFacesContextFromBundle("value_to_dd_bigger_from_current_value",
						"resources.Bundle", "error");

			} else {

				subbmissionExchnageFacade.submit(subbmissionExchnage, submissionDetailsCfg);

				subbmissionExchnage = new SubbmissionExchnage();
				CommonUtility.addMessageToFacesContextFromBundle("save",
						"resources.Bundle", "success");


			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public SubmissionDetailsCfg getSubmissionDetailsCfg() {
		return submissionDetailsCfg;
	}

	public void setSubmissionDetailsCfg(SubmissionDetailsCfg submissionDetailsCfg) {
		this.submissionDetailsCfg = submissionDetailsCfg;
	}

	public SubbmissionExchnage getSubbmissionExchnage() {
		return subbmissionExchnage;
	}

	public void setSubbmissionExchnage(SubbmissionExchnage subbmissionExchnage) {
		this.subbmissionExchnage = subbmissionExchnage;
	}

}
