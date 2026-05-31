package org.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import utils.CommonUtility;


@ManagedBean
@ViewScoped
public class test implements Serializable {
	private static final long serialVersionUID = -1L;
	
	public void zft() {
		CommonUtility.addMessageToFacesContext("update_succes" , "error");

		CommonUtility.addMessageToFacesContext("update_succes" , "error");

		CommonUtility.addMessageToFacesContext("update_succes" , "error");

	}

}
