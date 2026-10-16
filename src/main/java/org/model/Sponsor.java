package org.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import cfg.model.ReligionReferenceCfg;

@Entity
@Table(name = "sponser")
public class Sponsor extends MainEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private String fullName;

	private String phoneNo;

	@ManyToOne
	@JoinColumn(name = "RELIGIONREFERENCE_ID")
	private ReligionReferenceCfg religionReferenceCfg;

	private String livingArea;

	public Sponsor() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")
	private Sponsor(long id, String fullName, String phoneNo, ReligionReferenceCfg religionReferenceCfg,
			String livingArea) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.phoneNo = phoneNo;
		this.religionReferenceCfg = religionReferenceCfg;
		this.livingArea = livingArea;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public ReligionReferenceCfg getReligionReferenceCfg() {
		return religionReferenceCfg;
	}

	public void setReligionReferenceCfg(ReligionReferenceCfg religionReferenceCfg) {
		this.religionReferenceCfg = religionReferenceCfg;
	}

	public String getLivingArea() {
		return livingArea;
	}

	public void setLivingArea(String livingArea) {
		this.livingArea = livingArea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sponsor other = (Sponsor) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sponsor [fullName=" + fullName + ", phoneNo=" + phoneNo + "]";
	}

}
