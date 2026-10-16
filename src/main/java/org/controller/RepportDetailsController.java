package org.controller;

import java.io.UncheckedIOException;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import org.facade.ProvideAidFacade;
import org.facade.PurchaseVoucherFacade;
import org.facade.ReceiveMoneyFacade;
import org.facade.SubbmissionExchnageFacade;
import org.model.ProvideAidCompany;
import org.model.ProvideAidDesrvedPerson;
import org.model.ProvideAidRabet;
import org.model.PurchaseVoucherCoupon;
import org.model.ReceiveMoney;
import org.model.SubbmissionExchnage;
import org.omnifaces.util.Faces;

import cfg.facade.SubmissionDetailsCfgFacade;
import cfg.model.SubmissionDetailsCfg;
import enums.CurrencyEnum;
import utils.Utility;

@ViewScoped
@Named
public class RepportDetailsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SubmissionDetailsCfg submissionDetailsCfg;

	@Inject
	private SubmissionDetailsCfgFacade submissionDetailsCfgFacade;
	@Inject
	private ProvideAidFacade provideAidFacade;
	@Inject
	private PurchaseVoucherFacade purchaseVoucherFacade;

	private final String REQUEST_PARAM = "id";

	private Date fromDate;
	private Date toDate;

	private double totalValueLL;
	private double totalValueDD;

	/*
	 * Sponsor Gived
	 */
	private double totalValueLLSponsor;
	private double totalValueDDSponsor;
	private List<ReceiveMoney> listReceiveMoneySponsor = new ArrayList<>();
	private List<ReceiveMoney> filteredListMoneySponsor;
	@Inject
	private ReceiveMoneyFacade receiveMoneyFacade;

	/*
	 * desrved Given
	 */
	private double totalValueLLDesrved;
	private double totalValueDDDesrved;
	private List<ProvideAidDesrvedPerson> listDesrvedProvide = new ArrayList<>();
	private List<ProvideAidDesrvedPerson> filteredListDesrvedProvide;

	/*
	 * rabit
	 */

	private List<ProvideAidRabet> listRabit = new ArrayList<>();
	private List<ProvideAidRabet> filteredListRabit;

	private double totalValueLLRabit;
	private double totalValueDDRabit;

	/*
	 * Company
	 */

	private List<ProvideAidCompany> listCompanyAid = new ArrayList<>();
	private List<ProvideAidCompany> filteredListCompany;

	private double totalValueLLCompany;
	private double totalValueDDCompany;

	/*
	 * Mnel Kasa2im el shera2ye
	 */

	private List<PurchaseVoucherCoupon> listPurchaseVoucherCoupon = new ArrayList<>();
	private List<PurchaseVoucherCoupon> filteredPurchaseVoucherCoupon;

	private double totalValueLLPurchaseVoucher;

	/**
	 * Exchange
	 */

	private List<SubbmissionExchnage> listSubbmissionExchnage = new ArrayList<>();
	private List<SubbmissionExchnage> filteredSubbmissionExchnage;

	private double totalValueLLExchnage;
	private double totalvalueDDExchnage;

	@Inject
	private SubbmissionExchnageFacade subbmissionExchnageFacade;

	@PostConstruct
	public void init() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		if (!facesContext.isPostback()) {
			String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
			if (id != null && Double.parseDouble(id) > 0) {
				submissionDetailsCfg = submissionDetailsCfgFacade.find(Long.parseLong(id));
				if (submissionDetailsCfg == null) {
					try {
						Faces.redirect("exception");
					} catch (UncheckedIOException e) {
						e.printStackTrace();
					}
				}

			}
		}

	}

	public void search() {
		if (fromDate != null && toDate != null && fromDate.getTime() > toDate.getTime()) {
			Utility.addWarningMessage("from_date_bigger_to_date");

		} else {

			try {
				if (fromDate == null) {
					String sDateFrom = "31/12/1900";

					fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(sDateFrom);

				}
				if (toDate == null) {
					String sDateTo = "31/12/2100";
					toDate = new SimpleDateFormat("dd/MM/yyyy").parse(sDateTo);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Sponsor

			listReceiveMoneySponsor = receiveMoneyFacade.fillReport(null, fromDate, toDate, submissionDetailsCfg);

			if (listReceiveMoneySponsor != null && !listReceiveMoneySponsor.isEmpty()) {
				totalValueLLSponsor = listReceiveMoneySponsor.stream()
						.filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.Lira)).mapToDouble(x -> x.getValue())
						.sum();

				totalValueDDSponsor = listReceiveMoneySponsor.stream()
						.filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.DD)).mapToDouble(x -> x.getValue()).sum();
			}

			// provideDesrvedPerson
			listDesrvedProvide = provideAidFacade.findPersonDesrvedWithDate(submissionDetailsCfg, fromDate, toDate);

			if (listDesrvedProvide != null && !listDesrvedProvide.isEmpty()) {
				totalValueLLDesrved = listDesrvedProvide.stream()
						.filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.Lira)).mapToDouble(x -> x.getValue())
						.sum();

				totalValueDDDesrved = listDesrvedProvide.stream()
						.filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.DD)).mapToDouble(x -> x.getValue()).sum();
			}

			// Company

			listCompanyAid = provideAidFacade.findCompanyAidWithDate(submissionDetailsCfg, fromDate, toDate);

			if (listCompanyAid != null && !listCompanyAid.isEmpty()) {
				totalValueLLCompany = listCompanyAid.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.Lira))
						.mapToDouble(x -> x.getValue()).sum();

				totalValueDDCompany = listCompanyAid.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.DD))
						.mapToDouble(x -> x.getValue()).sum();
			}

			// Rabit

			listRabit = provideAidFacade.findRabitAidWithDate(submissionDetailsCfg, fromDate, toDate);

			if (listRabit != null && !listRabit.isEmpty()) {
				totalValueLLRabit = listRabit.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.Lira))
						.mapToDouble(x -> x.getValue()).sum();

				totalValueDDRabit = listRabit.stream().filter(x -> x.getCurrencyEnum().equals(CurrencyEnum.DD))
						.mapToDouble(x -> x.getValue()).sum();
			}

		}

		/*
		 * Kasa2im
		 */

		listPurchaseVoucherCoupon = purchaseVoucherFacade.findPurchaseVoucherCouponForGeneralRepport(fromDate, toDate,
				submissionDetailsCfg);

		if (listPurchaseVoucherCoupon != null && !listPurchaseVoucherCoupon.isEmpty()) {
			totalValueLLPurchaseVoucher = listPurchaseVoucherCoupon.stream().mapToDouble(x -> x.getValue() * x.getNbOFdesrvedPerson()).sum();

		}

		
		/*
		 * Exchangge
		 */
		
		listSubbmissionExchnage = subbmissionExchnageFacade.subbmissionExchnageBtwTwoDate(fromDate, toDate, submissionDetailsCfg) ;
		if(listSubbmissionExchnage !=null) {
			
			totalValueLLExchnage =  listSubbmissionExchnage.stream().mapToDouble(x -> x.getLiraValue()).sum();
			
			totalvalueDDExchnage =   listSubbmissionExchnage.stream().mapToDouble(x -> x.getDollarsValue()).sum();
			
			
		}
		
		totalValueLL = totalValueLLSponsor + totalValueLLExchnage
				- (totalValueLLRabit + totalValueLLCompany + totalValueLLPurchaseVoucher + totalValueLLDesrved);

		totalValueDD = totalValueDDSponsor - (totalValueDDRabit + totalValueDDCompany + totalValueDDDesrved + totalvalueDDExchnage) ;

		
		
		
		if(Double.compare(totalValueLL, 0) == 0 || Double.compare(totalValueDD, 0) == 0) {
			Utility.addWarningMessage("no_reccord");
		}
	}

	public SubmissionDetailsCfg getSubmissionDetailsCfg() {
		return submissionDetailsCfg;
	}

	public void setSubmissionDetailsCfg(SubmissionDetailsCfg submissionDetailsCfg) {
		this.submissionDetailsCfg = submissionDetailsCfg;
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

	public double getTotalValueLL() {
		return totalValueLL;
	}

	public void setTotalValueLL(double totalValueLL) {
		this.totalValueLL = totalValueLL;
	}

	public double getTotalValueDD() {
		return totalValueDD;
	}

	public void setTotalValueDD(double totalValueDD) {
		this.totalValueDD = totalValueDD;
	}

	public SubmissionDetailsCfgFacade getSubmissionDetailsCfgFacade() {
		return submissionDetailsCfgFacade;
	}

	public void setSubmissionDetailsCfgFacade(SubmissionDetailsCfgFacade submissionDetailsCfgFacade) {
		this.submissionDetailsCfgFacade = submissionDetailsCfgFacade;
	}

	public double getTotalValueLLSponsor() {
		return totalValueLLSponsor;
	}

	public void setTotalValueLLSponsor(double totalValueLLSponsor) {
		this.totalValueLLSponsor = totalValueLLSponsor;
	}

	public double getTotalValueDDSponsor() {
		return totalValueDDSponsor;
	}

	public void setTotalValueDDSponsor(double totalValueDDSponsor) {
		this.totalValueDDSponsor = totalValueDDSponsor;
	}

	public List<ReceiveMoney> getListReceiveMoneySponsor() {
		return listReceiveMoneySponsor;
	}

	public void setListReceiveMoneySponsor(List<ReceiveMoney> listReceiveMoneySponsor) {
		this.listReceiveMoneySponsor = listReceiveMoneySponsor;
	}

	public List<ReceiveMoney> getFilteredListMoneySponsor() {
		return filteredListMoneySponsor;
	}

	public void setFilteredListMoneySponsor(List<ReceiveMoney> filteredListMoneySponsor) {
		this.filteredListMoneySponsor = filteredListMoneySponsor;
	}

	public double getTotalValueLLDesrved() {
		return totalValueLLDesrved;
	}

	public void setTotalValueLLDesrved(double totalValueLLDesrved) {
		this.totalValueLLDesrved = totalValueLLDesrved;
	}

	public double getTotalValueDDDesrved() {
		return totalValueDDDesrved;
	}

	public void setTotalValueDDDesrved(double totalValueDDDesrved) {
		this.totalValueDDDesrved = totalValueDDDesrved;
	}

	public List<ProvideAidDesrvedPerson> getListDesrvedProvide() {
		return listDesrvedProvide;
	}

	public void setListDesrvedProvide(List<ProvideAidDesrvedPerson> listDesrvedProvide) {
		this.listDesrvedProvide = listDesrvedProvide;
	}

	public List<ProvideAidDesrvedPerson> getFilteredListDesrvedProvide() {
		return filteredListDesrvedProvide;
	}

	public void setFilteredListDesrvedProvide(List<ProvideAidDesrvedPerson> filteredListDesrvedProvide) {
		this.filteredListDesrvedProvide = filteredListDesrvedProvide;
	}

	public List<ProvideAidRabet> getListRabit() {
		return listRabit;
	}

	public void setListRabit(List<ProvideAidRabet> listRabit) {
		this.listRabit = listRabit;
	}

	public List<ProvideAidRabet> getFilteredListRabit() {
		return filteredListRabit;
	}

	public void setFilteredListRabit(List<ProvideAidRabet> filteredListRabit) {
		this.filteredListRabit = filteredListRabit;
	}

	public double getTotalValueLLRabit() {
		return totalValueLLRabit;
	}

	public void setTotalValueLLRabit(double totalValueLLRabit) {
		this.totalValueLLRabit = totalValueLLRabit;
	}

	public double getTotalValueDDRabit() {
		return totalValueDDRabit;
	}

	public void setTotalValueDDRabit(double totalValueDDRabit) {
		this.totalValueDDRabit = totalValueDDRabit;
	}

	public List<ProvideAidCompany> getListCompanyAid() {
		return listCompanyAid;
	}

	public void setListCompanyAid(List<ProvideAidCompany> listCompanyAid) {
		this.listCompanyAid = listCompanyAid;
	}

	public List<ProvideAidCompany> getFilteredListCompany() {
		return filteredListCompany;
	}

	public void setFilteredListCompany(List<ProvideAidCompany> filteredListCompany) {
		this.filteredListCompany = filteredListCompany;
	}

	public double getTotalValueLLCompany() {
		return totalValueLLCompany;
	}

	public void setTotalValueLLCompany(double totalValueLLCompany) {
		this.totalValueLLCompany = totalValueLLCompany;
	}

	public double getTotalValueDDCompany() {
		return totalValueDDCompany;
	}

	public void setTotalValueDDCompany(double totalValueDDCompany) {
		this.totalValueDDCompany = totalValueDDCompany;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<PurchaseVoucherCoupon> getListPurchaseVoucherCoupon() {
		return listPurchaseVoucherCoupon;
	}

	public void setListPurchaseVoucherCoupon(List<PurchaseVoucherCoupon> listPurchaseVoucherCoupon) {
		this.listPurchaseVoucherCoupon = listPurchaseVoucherCoupon;
	}

	public List<PurchaseVoucherCoupon> getFilteredPurchaseVoucherCoupon() {
		return filteredPurchaseVoucherCoupon;
	}

	public void setFilteredPurchaseVoucherCoupon(List<PurchaseVoucherCoupon> filteredPurchaseVoucherCoupon) {
		this.filteredPurchaseVoucherCoupon = filteredPurchaseVoucherCoupon;
	}

	public double getTotalValueLLPurchaseVoucher() {
		return totalValueLLPurchaseVoucher;
	}

	public void setTotalValueLLPurchaseVoucher(double totalValueLLPurchaseVoucher) {
		this.totalValueLLPurchaseVoucher = totalValueLLPurchaseVoucher;
	}

	public List<SubbmissionExchnage> getListSubbmissionExchnage() {
		return listSubbmissionExchnage;
	}

	public void setListSubbmissionExchnage(List<SubbmissionExchnage> listSubbmissionExchnage) {
		this.listSubbmissionExchnage = listSubbmissionExchnage;
	}

	public List<SubbmissionExchnage> getFilteredSubbmissionExchnage() {
		return filteredSubbmissionExchnage;
	}

	public void setFilteredSubbmissionExchnage(List<SubbmissionExchnage> filteredSubbmissionExchnage) {
		this.filteredSubbmissionExchnage = filteredSubbmissionExchnage;
	}

	public double getTotalValueLLExchnage() {
		return totalValueLLExchnage;
	}

	public void setTotalValueLLExchnage(double totalValueLLExchnage) {
		this.totalValueLLExchnage = totalValueLLExchnage;
	}

	public double getTotalvalueDDExchnage() {
		return totalvalueDDExchnage;
	}

	public void setTotalvalueDDExchnage(double totalvalueDDExchnage) {
		this.totalvalueDDExchnage = totalvalueDDExchnage;
	}

}
