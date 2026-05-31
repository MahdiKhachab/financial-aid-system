package org.facade;

import java.io.Serializable;

import org.model.PurchaseVoucherCoupon;
import org.model.Sponsor;

public class PurchaseVoucherCouponFacade extends AbstractFacade<PurchaseVoucherCoupon> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PurchaseVoucherCouponFacade() {
		super(PurchaseVoucherCoupon.class);
	}

}
