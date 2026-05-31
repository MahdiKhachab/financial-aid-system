package org.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import enums.NeedTypeEnum;

@Entity
@Table(name = "needdetails")
@NamedQueries({
		@NamedQuery(name = NeedDetails.findByWorker, query = "select nd.worker from NeedDetails nd where nd.worker = :worker"),
		@NamedQuery(name = NeedDetails.findByDeadNeed, query = "select nd from NeedDetails nd where nd.deadNeed = :need"),
		@NamedQuery(name = NeedDetails.findByDeadNeedWithConditions, query = "select nd from NeedDetails nd where nd.deadNeed = :need and nd.deadNeed.finished= :finished"),
		@NamedQuery(name = NeedDetails.findByDeadPerson, query = "select nd from NeedDetails nd  join nd.deadNeed need where need.deadPerson = :deadPerson") })
public class NeedDetails extends MainEntity implements Serializable ,Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String findByDeadNeed = "NeedDetails.findByDeadNeed";
	public static final String findByWorker = "NeedDetails.findByWorker";
	public static final String findByDeadNeedWithConditions = "NeedDetails.findByDeadNeedWithConditions";
	public static final String findByDeadPerson = "NeedDetails.findByDeadPerson";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@ManyToOne
	@JoinColumn(name = "WORKER_ID")
	private Worker worker;

	@ManyToOne
	@JoinColumn(name = "DEADNEED_ID")
	private DeadNeed deadNeed;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "NEEDDETAILS_ID")
	private List<Attachment> attachmentList = new ArrayList<Attachment>();

	private double cost;

	private double receivedCost;

	private int count;

	private NeedTypeEnum needType;

	private boolean finished;

	// private boolean workerReceiveMony;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String notes;

	@Transient
	private boolean forDelete;

	private int unitCount;

	public NeedDetails() {
		// TODO Auto-generated constructor stub
	}

	public double getReceivedCost() {
		return receivedCost;
	}

	public void setReceivedCost(double receivedCost) {
		this.receivedCost = receivedCost;
	}

	// public boolean isWorkerReceiveMony() {
	// return workerReceiveMony;
	// }
	//
	// public void setWorkerReceiveMony(boolean workerReceiveMony) {
	// this.workerReceiveMony = workerReceiveMony;
	// }

	public void addToAttachment(Attachment attachment) {
		this.attachmentList.add(attachment);
	}

	public void removeFromAttachment(Attachment attachment) {
		this.attachmentList.remove(attachment);
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public boolean isForDelete() {
		return forDelete;
	}

	public void setForDelete(boolean forDelete) {
		this.forDelete = forDelete;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	// public Date getDate() {
	// return date;
	// }
	//
	// public void setDate(Date date) {
	// this.date = date;
	// }

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public DeadNeed getDeadNeed() {
		return deadNeed;
	}

	public void setDeadNeed(DeadNeed deadNeed) {
		this.deadNeed = deadNeed;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
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

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getUnitCount() {
		return unitCount;
	}

	public void setUnitCount(int unitCount) {
		this.unitCount = unitCount;
	}
	 
	public NeedDetails clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (NeedDetails) super.clone();
	}
	

}
