package org.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.DeservedFacade;
import org.facade.SponsorFacade;

import cfg.facade.SubmissionCfgFacade;
import cfg.model.SubmissionCfg;
import cfg.model.SubmissionDetailsCfg;

@ViewScoped
@Named
public class IndexController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int nbOfDeserved;

	private int nbOfSponsors;

	private double totalMoneyInLira;

	private double totalMoneyInDollars;

	@Inject
	private SponsorFacade sponsorFacade;

	@Inject
	private DeservedFacade deservedFacade;

	@Inject
	private SubmissionCfgFacade submissionCfgFacade;

	@PostConstruct
	public void init() {
		nbOfDeserved = deservedFacade.findAll().size();
		nbOfSponsors = sponsorFacade.findAll().size();

		calculationOfMoney();
	}

	public void calculationOfMoney() {
		List<SubmissionCfg> pageItems = submissionCfgFacade.findAll();

		pageItems.stream().forEach(

				(x -> {
					for (SubmissionDetailsCfg var : x.getSubmissionDetailsList()) {
						totalMoneyInLira += var.getBalance_ll();
					}
				}));

		pageItems.stream().forEach((x -> {
			for (SubmissionDetailsCfg var : x.getSubmissionDetailsList()) {
				totalMoneyInDollars += var.getBalance_dd();
			}
		}));
	}

	public int getNbOfDeserved() {
		return nbOfDeserved;
	}

	public void setNbOfDeserved(int nbOfDeserved) {
		this.nbOfDeserved = nbOfDeserved;
	}

	public int getNbOfSponsors() {
		return nbOfSponsors;
	}

	public void setNbOfSponsors(int nbOfSponsors) {
		this.nbOfSponsors = nbOfSponsors;
	}

	public double getTotalMoneyInLira() {
		return totalMoneyInLira;
	}

	public void setTotalMoneyInLira(double totalMoneyInLira) {
		this.totalMoneyInLira = totalMoneyInLira;
	}

	public double getTotalMoneyInDollars() {
		return totalMoneyInDollars;
	}

	public void setTotalMoneyInDollars(double totalMoneyInDollars) {
		this.totalMoneyInDollars = totalMoneyInDollars;
	}

}
