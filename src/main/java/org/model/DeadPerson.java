package org.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "deadperson")
public class DeadPerson extends MainEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private String deadName;

	private String deadMotherName;

	private String delegator;

	private String delegatorPhone;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "deadPerson",fetch=FetchType.EAGER)
	@JoinColumn(name = "DEADPERSON_ID")
	private List<DeadNeed> deadNeedList = new ArrayList<DeadNeed>();

	public DeadPerson() {
		// TODO Auto-generated constructor stub
	}

	public List<DeadNeed> getDeadNeedList() {
		return deadNeedList;
	}

	public void setDeadNeedList(List<DeadNeed> deadNeedList) {
		this.deadNeedList = deadNeedList;
	}

	public void addToDeadNeedList(DeadNeed deadNeed) {
		deadNeed.setDeadPerson(this);
		this.deadNeedList.add(deadNeed);
	}

	public void removeFromDeadNeed(DeadNeed deadNeed) {
		this.deadNeedList.remove(deadNeed);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDeadName() {
		return deadName;
	}

	public void setDeadName(String deadName) {
		this.deadName = deadName;
	}

	public String getDeadMotherName() {
		return deadMotherName;
	}

	public void setDeadMotherName(String deadMotherName) {
		this.deadMotherName = deadMotherName;
	}

	public String getDelegator() {
		return delegator;
	}

	public void setDelegator(String delegator) {
		this.delegator = delegator;
	}

	public String getDelegatorPhone() {
		return delegatorPhone;
	}

	public void setDelegatorPhone(String delegatorPhone) {
		this.delegatorPhone = delegatorPhone;
	}


}
