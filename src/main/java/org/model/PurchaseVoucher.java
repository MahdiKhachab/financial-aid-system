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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQueries({
		@NamedQuery(name = PurchaseVoucher.check_if_related_with_deserved, query = "select pv from PurchaseVoucher pv join pv.listDeserved ds where ds = :deserved "),

		@NamedQuery(name = PurchaseVoucher.GET_PURCHASE_VOUCHER_BY_DATE, query = "SELECT Purchase FROM PurchaseVoucher Purchase WHERE (Purchase.creationDate >= COALESCE(:fromDate,  cast (Purchase.creationDate  TIMESTAMP ))) and  (Purchase.creationDate <= COALESCE(:toDate, cast(  Purchase.creationDate  as timestamp ) ))    "),
		@NamedQuery(name = PurchaseVoucher.GET_PURCHASE_VOUCHER_BY_DATE_STORE, query = "SELECT Purchase FROM PurchaseVoucher Purchase WHERE (Purchase.creationDate >= COALESCE(:fromDate,  cast (Purchase.creationDate  TIMESTAMP ))) and  (Purchase.creationDate <= COALESCE(:toDate, cast(  Purchase.creationDate  as timestamp ) )) and Purchase.stores = :store  and PURCHASE.paied =:paied  ")

})

@Entity
@Table(name = "purchase_voucher")
public class PurchaseVoucher extends MainEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String GET_PURCHASE_VOUCHER_BY_DATE = "PurchaseVoucher_Get_Purchase_Voucher_By_Date";
	public static final String GET_PURCHASE_VOUCHER_BY_DATE_STORE = "PurchaseVoucher_Get_Purchase_Voucher_By_Date_Store";
	public static final String check_if_related_with_deserved = "purchase_voucher.check_if_related_with_deserved";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@ManyToOne
	@JoinColumn(name = "stores_id")
	private Stores stores;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "purchase_voucher_id")
	private List<PurchaseVoucherCoupon> listPurchaseVoucherCoupon = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "purchase_voucher_desrved", joinColumns = @JoinColumn(name = "purchase_voucher_id"), inverseJoinColumns = @JoinColumn(name = "deserved_id"))
	private List<Deserved> listDeserved = new ArrayList<>();

	private double totalCost;

	// مدفوعة
	private boolean paied = false;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "PURCHASEVOUCHER_ID")
	private List<Attachment> attachmentList = new ArrayList<Attachment>();

	public PurchaseVoucher(long id, Stores stores, List<PurchaseVoucherCoupon> listPurchaseVoucherCoupon,
			List<Deserved> listDeserved, double totalCost, boolean paied) {
		super();
		this.id = id;
		this.stores = stores;
		this.listPurchaseVoucherCoupon = listPurchaseVoucherCoupon;
		this.listDeserved = listDeserved;
		this.totalCost = totalCost;
		this.paied = paied;
	}

	public PurchaseVoucher() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Stores getStores() {
		return stores;
	}

	public void setStores(Stores stores) {
		this.stores = stores;
	}

	public List<PurchaseVoucherCoupon> getListPurchaseVoucherCoupon() {
		return listPurchaseVoucherCoupon;
	}

	public void setListPurchaseVoucherCoupon(List<PurchaseVoucherCoupon> listPurchaseVoucherCoupon) {
		this.listPurchaseVoucherCoupon = listPurchaseVoucherCoupon;
	}

	public List<Deserved> getListDeserved() {
		return listDeserved;
	}

	public void setListDeserved(List<Deserved> listDeserved) {
		this.listDeserved = listDeserved;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public boolean isPaied() {
		return paied;
	}

	public void setPaied(boolean paied) {
		this.paied = paied;
	}

}
