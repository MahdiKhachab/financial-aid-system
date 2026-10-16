package org.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import cfg.model.CompanyCfg;
import enums.CurrencyEnum;
import enums.MediatorMonyEnum;

@Entity
@Table(name = "mediator")
@NamedQueries({
		@NamedQuery(name = Mediator.NOTDELIVEREDDOLLARS, query = "select sum(m.ammount) from Mediator m where m.currency = :dd and m.delivered = false"),
		@NamedQuery(name = Mediator.NOTDELIVEREDLIRA, query = "select sum(m.ammount) from Mediator m where m.currency = :ll and m.delivered = false"),
		@NamedQuery(name = Mediator.NOTDELIVEREDWITHCOMPANY, query = "select New org.dtos.CompanyDetailsDto(m.toCompany,sum(m.ammount),m.currency) from Mediator m where m.currency = :currency and m.delivered = false group by m.toCompany,m.currency") })
public class Mediator extends MainEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String NOTDELIVEREDDOLLARS = "mediator.notDeliveredDollar";
	public static final String NOTDELIVEREDLIRA = "mediator.notDeliveredLira";
	public static final String NOTDELIVEREDWITHCOMPANY = "mediator.notDeliveredWithCompnay";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private String delegateName;

	private String phoneNo;

	@ManyToOne
	@JoinColumn(name = "SPONSOR_ID")
	private Sponsor fromSponsor;

	@ManyToOne
	@JoinColumn(name = "COMPANY_ID")
	private CompanyCfg toCompany;

	private double ammount;

	@Enumerated(EnumType.STRING)
	@Column(name = "moneytype")
	private MediatorMonyEnum monyEnum;

	@Enumerated(EnumType.STRING)
	private CurrencyEnum currency;

	@Temporal(TemporalType.DATE)
	private Date receiveDate;

	private boolean delivered;

	private String receiverOfCompany;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "MEDIATOR_ID")
	private List<Attachment> attachmentList = new ArrayList<Attachment>();

	public Mediator() {
	}

	public void addToAttachment(Attachment attachment) {
		this.attachmentList.add(attachment);
	}

	public void removeFromAttachment(Attachment attachment) {
		this.attachmentList.remove(attachment);
	}

	public MediatorMonyEnum getMonyEnum() {
		return monyEnum;
	}

	public void setMonyEnum(MediatorMonyEnum monyEnum) {
		this.monyEnum = monyEnum;
	}

	public String getReceiverOfCompany() {
		return receiverOfCompany;
	}

	public void setReceiverOfCompany(String receiverOfCompany) {
		this.receiverOfCompany = receiverOfCompany;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDelegateName() {
		return delegateName;
	}

	public void setDelegateName(String delegateName) {
		this.delegateName = delegateName;
	}

	public double getAmmount() {
		return ammount;
	}

	public void setAmmount(double ammount) {
		this.ammount = ammount;
	}

	public CurrencyEnum getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyEnum currency) {
		this.currency = currency;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public CompanyCfg getToCompany() {
		return toCompany;
	}

	public void setToCompany(CompanyCfg toCompany) {
		this.toCompany = toCompany;
	}

	public Sponsor getFromSponsor() {
		return fromSponsor;
	}

	public void setFromSponsor(Sponsor fromSponsor) {
		this.fromSponsor = fromSponsor;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

}
