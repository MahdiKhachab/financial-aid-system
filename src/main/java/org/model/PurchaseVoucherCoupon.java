package org.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import cfg.model.SubmissionDetailsCfg;

@Entity
@Table(name = "purchase_voucher_coupon")
@NamedQueries({
		@NamedQuery(name = PurchaseVoucherCoupon.GET_PURCHASE_VOUCHER_FOR_GENERAL_REPPORT, query = "SELECT Purchase FROM PurchaseVoucherCoupon Purchase WHERE Purchase.creationDate between :fromDate and :toDate and PURCHASE.submissionDetailsCfg = :submissionDetailsCfg  ")

})
public class PurchaseVoucherCoupon extends MainEntity implements Serializable {

	public static final String GET_PURCHASE_VOUCHER_FOR_GENERAL_REPPORT = "Purchase_Voucher_For_General_Repport";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	// yaane ade el ime 3ala el LL
	private double value;

	@ManyToOne
	@JoinColumn(name = "submissionDetailsCfg_id")
	private SubmissionDetailsCfg submissionDetailsCfg;

	private int nbOFdesrvedPerson;

	public int getNbOFdesrvedPerson() {
		return nbOFdesrvedPerson;
	}

	public void setNbOFdesrvedPerson(int nbOFdesrvedPerson) {
		this.nbOFdesrvedPerson = nbOFdesrvedPerson;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((submissionDetailsCfg == null) ? 0 : submissionDetailsCfg.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchaseVoucherCoupon other = (PurchaseVoucherCoupon) obj;
		if (id != other.id)
			return false;
		if (submissionDetailsCfg == null) {
			if (other.submissionDetailsCfg != null)
				return false;
		} else if (!submissionDetailsCfg.equals(other.submissionDetailsCfg))
			return false;
		return true;
	}

}
