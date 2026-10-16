package org.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import cfg.model.SubmissionDetailsCfg;
import enums.CurrencyEnum;

/*@NamedQueries({
		@NamedQuery(name = ReceiveMoney.FILTER_SPONSOR_REPPORT, query = "SELECT re FROM ReceiveMoney re  where   re.sponser = :sponsor and re.date  BETWEEN :fromDate and :toDate  and (     :submissionDetailsCfg IS   NULL OR     ( re.submissionDetailsCfg.id  =  :submissionDetailsCfg      )) ")

})*/

@Entity
@Table(name = "receive_money")
public class ReceiveMoney extends MainEntity implements Serializable {

	/*
	 * public static final String FILTER_SPONSOR_REPPORT =
	 * "receive_money_filter_sponsor_repport";
	 */

	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	private static final long serialVersionUID = 1L;
	@Enumerated(EnumType.STRING)
	private CurrencyEnum currencyEnum;

	private double value;

	private String note;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@ManyToOne
	@JoinColumn(name = "sponser_id")
	private Sponsor sponser;

	@ManyToOne
	@JoinColumn(name = "submissionDetailsCfg_id")
	private SubmissionDetailsCfg submissionDetailsCfg;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CurrencyEnum getCurrencyEnum() {
		return currencyEnum;
	}

	public void setCurrencyEnum(CurrencyEnum currencyEnum) {
		this.currencyEnum = currencyEnum;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Sponsor getSponser() {
		return sponser;
	}

	public void setSponser(Sponsor sponser) {
		this.sponser = sponser;
	}

	public SubmissionDetailsCfg getSubmissionDetailsCfg() {
		return submissionDetailsCfg;
	}

	public void setSubmissionDetailsCfg(SubmissionDetailsCfg submissionDetailsCfg) {
		this.submissionDetailsCfg = submissionDetailsCfg;
	}

}
