package org.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.model.MainEntity;

import cfg.model.ReligionReferenceCfg;
import cfg.model.Service;

@Entity
@Table(name = "worker")
@NamedQueries({
		@NamedQuery(name = Worker.findByService, query = "select w from Worker w join w.services s  where s=:service") })
public class Worker extends MainEntity implements Serializable {

	/**
	 * 
	 */
	public static final String findByService = "Worker.findByService";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private String name;

	private String phoneNo;

	private String livingArea;

	private String notes;

	@OneToOne
	@JoinColumn(name = "RELIGION_ID")
	private ReligionReferenceCfg religion;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "worker_service", joinColumns = @JoinColumn(name = "WORKER_ID"), inverseJoinColumns = @JoinColumn(name = "SERVICE_ID"))
	private List<Service> services = new ArrayList<>();

	@Transient
	private boolean editOrDelete = true;

	public Worker() {
	}

	public void addService(Service service) {
		services.add(service);
		service.getWorkers().add(this);
	}

	public void removeService(Service service) {
		services.remove(service);
		service.getWorkers().remove(this);
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLivingArea() {
		return livingArea;
	}

	public void setLivingArea(String livingArea) {
		this.livingArea = livingArea;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public ReligionReferenceCfg getReligion() {
		return religion;
	}

	public void setReligion(ReligionReferenceCfg religion) {
		this.religion = religion;
	}

	public boolean isEditOrDelete() {
		return editOrDelete;
	}

	public void setEditOrDelete(boolean editOrDelete) {
		this.editOrDelete = editOrDelete;
	}

}
