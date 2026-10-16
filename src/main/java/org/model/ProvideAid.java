package org.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import cfg.model.SubmissionDetailsCfg;
import enums.CurrencyEnum;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ProvideAid extends MainEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	private static final long serialVersionUID = 1L;
    @Enumerated(EnumType.STRING)
	private CurrencyEnum currencyEnum;

	private double value;
	@Temporal(TemporalType.TIMESTAMP)

	private Date deliveryDate;

	@ManyToOne
	@JoinColumn(name = "submissionDetailsCfg_id")
	private SubmissionDetailsCfg submissionDetailsCfg;

	private String note;

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

	public SubmissionDetailsCfg getSubmissionDetailsCfg() {
		return submissionDetailsCfg;
	}

	public void setSubmissionDetailsCfg(SubmissionDetailsCfg submissionDetailsCfg) {
		this.submissionDetailsCfg = submissionDetailsCfg;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
