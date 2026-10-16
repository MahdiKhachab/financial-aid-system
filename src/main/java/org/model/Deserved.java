package org.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import cfg.model.HealthyStatusCfg;
import cfg.model.InssuranceCfg;
import cfg.model.PoorPercentageCfg;
import cfg.model.ReligionReferenceCfg;
import enums.PersonGenre;

@Entity
@Table(name = "deserved")
public class Deserved extends MainEntity implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private String fullName;

	private String phoneNo;

	private int nbOfIndividuals;

	private String livingArea;

	private boolean working;

	private double salary;

	private boolean hasCar;

	private int nbOfExpatiateChildren;

	private boolean richLogical;

	private String knownBy;

	@ManyToOne
	@JoinColumn(name = "HEALTHYSTATUS_ID")
	private HealthyStatusCfg healthyStatusCfg;

	@ManyToOne
	@JoinColumn(name = "INSSURANCE_ID")
	private InssuranceCfg inssuranceCfg;

	@ManyToOne
	@JoinColumn(name = "POORPERCENTAGE_ID")
	private PoorPercentageCfg poorPercentageCfg;

	@ManyToOne
	@JoinColumn(name = "RELIGIONREFERENCE_ID")
	private ReligionReferenceCfg religionReferenceCfg;

	@Enumerated(EnumType.STRING)
	private PersonGenre personGenre;

	private String notes;

	public Deserved() {
		// TODO Auto-generated constructor stub
	}

	public boolean isRichLogical() {
		return richLogical;
	}

	public void setRichLogical(boolean richLogical) {
		this.richLogical = richLogical;
	}

	public String getKnownBy() {
		return knownBy;
	}

	public void setKnownBy(String knownBy) {
		this.knownBy = knownBy;
	}

	public String getLivingArea() {
		return livingArea;
	}

	public void setLivingArea(String livingArea) {
		this.livingArea = livingArea;
	}

	public boolean isWorking() {
		return working;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}

	public HealthyStatusCfg getHealthyStatusCfg() {
		return healthyStatusCfg;
	}

	public void setHealthyStatusCfg(HealthyStatusCfg healthyStatusCfg) {
		this.healthyStatusCfg = healthyStatusCfg;
	}

	public InssuranceCfg getInssuranceCfg() {
		return inssuranceCfg;
	}

	public void setInssuranceCfg(InssuranceCfg inssuranceCfg) {
		this.inssuranceCfg = inssuranceCfg;
	}

	public PoorPercentageCfg getPoorPercentageCfg() {
		return poorPercentageCfg;
	}

	public void setPoorPercentageCfg(PoorPercentageCfg poorPercentageCfg) {
		this.poorPercentageCfg = poorPercentageCfg;
	}

	public ReligionReferenceCfg getReligionReferenceCfg() {
		return religionReferenceCfg;
	}

	public void setReligionReferenceCfg(ReligionReferenceCfg religionReferenceCfg) {
		this.religionReferenceCfg = religionReferenceCfg;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public int getNbOfIndividuals() {
		return nbOfIndividuals;
	}

	public void setNbOfIndividuals(int nbOfIndividuals) {
		this.nbOfIndividuals = nbOfIndividuals;
	}

	public boolean isHasCar() {
		return hasCar;
	}

	public void setHasCar(boolean hasCar) {
		this.hasCar = hasCar;
	}

	public int getNbOfExpatiateChildren() {
		return nbOfExpatiateChildren;
	}

	public void setNbOfExpatiateChildren(int nbOfExpatiateChildren) {
		this.nbOfExpatiateChildren = nbOfExpatiateChildren;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public PersonGenre getPersonGenre() {
		return personGenre;
	}

	public void setPersonGenre(PersonGenre personGenre) {
		this.personGenre = personGenre;
	}

}
