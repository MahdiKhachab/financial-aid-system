package org.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttributeType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.eclipse.persistence.jpa.rs.util.HrefHelper;

import cfg.model.Service;
import enums.CurrencyEnum;
import enums.NeedTypeEnum;
import utils.Utility;

@Entity
@Table(name = "deadneed")
@NamedQueries({
		@NamedQuery(name = DeadNeed.findByService, query = "select dn from DeadNeed dn where dn.service= :service and dn.finished = false") })
public class DeadNeed extends MainEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String findByService = "DeadNeed.findByService";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private int count;

	private NeedTypeEnum needType;

	private double amount;

	private CurrencyEnum currency;

	private boolean finished;

	@ManyToOne
	@JoinColumn(name = "SERVICE_ID")
	private Service service;

	@ManyToOne
	private DeadPerson deadPerson;

	private int unitCount;
	@Transient
	private boolean forDelete;

	public DeadNeed() {
	}

	public boolean isForDelete() {
		return forDelete;
	}

	public void setForDelete(boolean forDelete) {
		this.forDelete = forDelete;
	}

	public int getUnitCount() {
		return unitCount;
	}

	public void setUnitCount(int unitCount) {
		this.unitCount = unitCount;
	}

	public DeadPerson getDeadPerson() {
		return deadPerson;
	}

	public void setDeadPerson(DeadPerson deadPerson) {
		this.deadPerson = deadPerson;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public NeedTypeEnum getNeedType() {
		return needType;
	}

	public void setNeedType(NeedTypeEnum needType) {
		this.needType = needType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public CurrencyEnum getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyEnum currency) {
		this.currency = currency;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	// @PrePersist
	// @PreUpdate
	// public void setUnitCountByType() {
	//
	// if (this.needType == NeedTypeEnum.DAYS)
	// setUnitCount(count);
	//
	// if (this.needType == NeedTypeEnum.MONTHS)
	// setUnitCount(count * 30);
	//
	// if (this.needType == NeedTypeEnum.YEARS)
	// setUnitCount(count * 365);
	//
	// if (this.needType == NeedTypeEnum.NOT_SET)
	// setUnitCount(1);
	// }
}
