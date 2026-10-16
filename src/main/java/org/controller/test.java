package org.controller;

import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import utils.CommonUtility;


@Named("test")
@RequestScoped
public class test implements Serializable {
	private static final long serialVersionUID = -1L;
	
	public void zft() {
		CommonUtility.addMessageToFacesContext("update_succes" , "error");

		CommonUtility.addMessageToFacesContext("update_succes" , "error");

		CommonUtility.addMessageToFacesContext("update_succes" , "error");

	}

}
