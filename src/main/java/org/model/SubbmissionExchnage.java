package org.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import cfg.model.SubmissionDetailsCfg;

@NamedQueries({
		@NamedQuery(name = SubbmissionExchnage.QUERY_BTW_TWO_DATE, query = "select s from   SubbmissionExchnage s where   s.creationDate between :dateFrom and :dateTo and s.submissionDetailsCfg = :submissionDetailsCfg")

})

@Entity
@Table(name = "subbmission_exchnage")
public class SubbmissionExchnage extends MainEntity implements Serializable {

	public final static String QUERY_BTW_TWO_DATE = "SubbmissionExchnage.QeuryBtwTwoDate";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	@Temporal(TemporalType.TIMESTAMP)
	public Date date;

	@ManyToOne
	@JoinColumn(name = "submissionDetailsCfg_id")
	private SubmissionDetailsCfg submissionDetailsCfg;

	public double rate;
	public double liraValue;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getLiraValue() {
		return liraValue;
	}

	public void setLiraValue(double liraValue) {
		this.liraValue = liraValue;
	}

	public double getDollarsValue() {
		return liraValue / rate;
	}

	public SubmissionDetailsCfg getSubmissionDetailsCfg() {
		return submissionDetailsCfg;
	}

	public void setSubmissionDetailsCfg(SubmissionDetailsCfg submissionDetailsCfg) {
		this.submissionDetailsCfg = submissionDetailsCfg;
	}

}