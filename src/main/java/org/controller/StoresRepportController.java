package org.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.facade.PurchaseVoucherFacade;
import org.model.Attachment;
import org.model.PurchaseVoucher;
import org.model.Stores;
import org.omnifaces.util.Faces;

import utils.Utility;

@ViewScoped
@Named
public class StoresRepportController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date fromDate;
	private Date toDate;

	@Inject
	private PurchaseVoucherFacade purchaseVoucherFacade;

	private List<PurchaseVoucher> listPurchaseVoucher = new ArrayList<>();
	private List<PurchaseVoucher> listFilter;
	private PurchaseVoucher purchaseVoucher=new PurchaseVoucher();
	private Stores stores;

	private boolean paied;

	@PostConstruct
	public void init() {

	}

	public void seacrh() {
		try {
			listPurchaseVoucher = purchaseVoucherFacade.findPurchaseVoucherByDateAndStore(fromDate, toDate, stores,
					paied);

			if (listPurchaseVoucher.isEmpty()) {
				Utility.addWarningMessage("no_purchase_data_for_this_date");
			}

		} catch (Exception e) {
			Utility.addErrorMessage("error_on_search");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void select(PurchaseVoucher item) {
		this.purchaseVoucher = item;
	}

	public void download(Attachment attachment) {
		try {
			Faces.sendFile(attachment.getData(), attachment.getFileName(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteAttachment(Attachment attachment) {
		try {
			this.purchaseVoucher.removeFromAttachment(attachment);
			purchaseVoucher = this.purchaseVoucherFacade.save(purchaseVoucher);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}


	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<PurchaseVoucher> getListPurchaseVoucher() {
		return listPurchaseVoucher;
	}

	public void setListPurchaseVoucher(List<PurchaseVoucher> listPurchaseVoucher) {
		this.listPurchaseVoucher = listPurchaseVoucher;
	}

	public List<PurchaseVoucher> getListFilter() {
		return listFilter;
	}

	public void setListFilter(List<PurchaseVoucher> listFilter) {
		this.listFilter = listFilter;
	}

	public PurchaseVoucher getPurchaseVoucher() {
		return purchaseVoucher;
	}

	public void setPurchaseVoucher(PurchaseVoucher purchaseVoucher) {
		this.purchaseVoucher = purchaseVoucher;
	}

	public Stores getStores() {
		return stores;
	}

	public void setStores(Stores stores) {
		this.stores = stores;
	}

	public boolean isPaied() {
		return paied;
	}

	public void setPaied(boolean paied) {
		this.paied = paied;
	}

}
