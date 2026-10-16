package org.facade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.inject.Inject;
import jakarta.persistence.TypedQuery;

import org.model.Deserved;
import org.model.PurchaseVoucher;
import org.model.PurchaseVoucherCoupon;
import org.model.Stores;

import cfg.facade.SubmissionDetailsCfgFacade;
import cfg.model.SubmissionDetailsCfg;

@Service
@Transactional
public class PurchaseVoucherFacade extends AbstractFacade<PurchaseVoucher> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject

	private SubmissionDetailsCfgFacade submissionDetailsCfgFacade;

	public PurchaseVoucherFacade() {
		super(PurchaseVoucher.class);
	}

	public PurchaseVoucher save2(PurchaseVoucher purchaseVoucher, int OrginalList) throws Exception {

		for (PurchaseVoucherCoupon purchaseVoucherCoupon : purchaseVoucher.getListPurchaseVoucherCoupon()) {

			purchaseVoucherCoupon.setNbOFdesrvedPerson(purchaseVoucher.getListDeserved().size());
			submissionDetailsCfgFacade.updateValueFromAid(purchaseVoucherCoupon.getSubmissionDetailsCfg(),
					purchaseVoucherCoupon.getValue() * (purchaseVoucher.getListDeserved().size() - OrginalList), true);
		}

		return this.save(purchaseVoucher);
	}
	
	
	public PurchaseVoucher saveOnDeleteSubbimtiton(SubmissionDetailsCfg submissionDetailsCfg , PurchaseVoucher purchaseVoucher , double  totoalValue  ) throws Exception {
		submissionDetailsCfgFacade.updateValuesFromSponsor(submissionDetailsCfg , totoalValue  , true ) ;
		
		
		
		return this.save(purchaseVoucher);

	}

	public List<PurchaseVoucher> findPurchaseVoucherByDate(Date fromDate, Date toDate) throws Exception {
		String sDateFrom = "31/12/1900";
		String sDateTo = "31/12/2100";
		Date dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(sDateFrom);
		Date dateTo = new SimpleDateFormat("dd/MM/yyyy").parse(sDateTo);

		return getEntityManager().createNamedQuery(PurchaseVoucher.GET_PURCHASE_VOUCHER_BY_DATE, PurchaseVoucher.class)
				.setParameter("fromDate", fromDate == null ? dateFrom : fromDate)
				.setParameter("toDate", toDate == null ? dateTo : toDate)

				.getResultList();
	}

	public List<PurchaseVoucher> findPurchaseVoucherByDateAndStore(Date fromDate, Date toDate, Stores store,
			boolean paied) throws Exception {
		String sDateFrom = "31/12/1900";
		String sDateTo = "31/12/2100";
		Date dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(sDateFrom);
		Date dateTo = new SimpleDateFormat("dd/MM/yyyy").parse(sDateTo);

		return getEntityManager()
				.createNamedQuery(PurchaseVoucher.GET_PURCHASE_VOUCHER_BY_DATE_STORE, PurchaseVoucher.class)
				.setParameter("fromDate", fromDate == null ? dateFrom : fromDate)
				.setParameter("toDate", toDate == null ? dateTo : toDate).setParameter("store", store)
				.setParameter("paied", paied).getResultList();
	}

	public List<PurchaseVoucherCoupon> findPurchaseVoucherCouponForGeneralRepport(Date fromDate, Date toDate,
			SubmissionDetailsCfg submissionDetailsCfg) {

		return getEntityManager()
				.createNamedQuery(PurchaseVoucherCoupon.GET_PURCHASE_VOUCHER_FOR_GENERAL_REPPORT,
						PurchaseVoucherCoupon.class)
				.setParameter("fromDate", fromDate == null ? fromDate : fromDate)
				.setParameter("toDate", toDate == null ? toDate : toDate)
				.setParameter("submissionDetailsCfg", submissionDetailsCfg).getResultList();

	}

	public boolean checkIfRelatedWithDeserved(Deserved deserved) throws Exception {

		TypedQuery<Boolean> query = getEntityManager()
				.createNamedQuery(PurchaseVoucher.check_if_related_with_deserved, Boolean.class)
				.setParameter("deserved", deserved);

		if (query.getResultList() == null || query.getResultList().isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

}
