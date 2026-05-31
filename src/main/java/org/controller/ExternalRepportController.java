package org.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.ExternalRepportFacade;

import cfg.model.SubmissionDetailsCfg;
import enums.CurrencyEnum;

@ViewScoped
@Named
public class ExternalRepportController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SubmissionDetailsCfg> allSubmissionDetailsCfgList = new ArrayList<>();
	private List<SubmissionDetailsCfg> allSubmissionDetailsCfgListFilterd;

	@Inject
	private ExternalRepportFacade externalRepportFacade;

	private double totalValueDD;
	private double totalValueLL;

	@PostConstruct
	public void init() {

		allSubmissionDetailsCfgList = externalRepportFacade.getAllSubmissionDetailsCfg();
		totalValueLL = allSubmissionDetailsCfgList.stream().mapToDouble(x -> x.getBalance_ll()).sum();

		totalValueDD = allSubmissionDetailsCfgList.stream().mapToDouble(x -> x.getBalance_dd()).sum();
	}

	public List<SubmissionDetailsCfg> getAllSubmissionDetailsCfgList() {
		return allSubmissionDetailsCfgList;
	}

	public void setAllSubmissionDetailsCfgList(List<SubmissionDetailsCfg> allSubmissionDetailsCfgList) {
		this.allSubmissionDetailsCfgList = allSubmissionDetailsCfgList;
	}

	public List<SubmissionDetailsCfg> getAllSubmissionDetailsCfgListFilterd() {
		return allSubmissionDetailsCfgListFilterd;
	}

	public void setAllSubmissionDetailsCfgListFilterd(List<SubmissionDetailsCfg> allSubmissionDetailsCfgListFilterd) {
		this.allSubmissionDetailsCfgListFilterd = allSubmissionDetailsCfgListFilterd;
	}

	public double getTotalValueDD() {
		return totalValueDD;
	}

	public void setTotalValueDD(double totalValueDD) {
		this.totalValueDD = totalValueDD;
	}

	public double getTotalValueLL() {
		return totalValueLL;
	}

	public void setTotalValueLL(double totalValueLL) {
		this.totalValueLL = totalValueLL;
	}

}
