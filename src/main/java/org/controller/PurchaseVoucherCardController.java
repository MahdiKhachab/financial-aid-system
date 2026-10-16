package org.controller;

import java.io.UncheckedIOException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.facade.DeservedFacade;
import org.facade.ProvideAidFacade;
import org.facade.PurchaseVoucherFacade;
import org.facade.StoresFacade;
import org.model.Attachment;
import org.model.Deserved;
import org.model.PurchaseVoucher;
import org.model.PurchaseVoucherCoupon;
import org.model.Stores;
import org.omnifaces.util.Faces;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;

import cfg.facade.HealthyStatusCfgFacade;
import cfg.facade.InssuranceCfgFacade;
import cfg.facade.PoorPercenatageCfgFacade;
import cfg.facade.ReligionReferenceCfgFacade;
import cfg.model.HealthyStatusCfg;
import cfg.model.InssuranceCfg;
import cfg.model.PoorPercentageCfg;
import cfg.model.ReligionReferenceCfg;
import cfg.model.SubmissionDetailsCfg;
import utils.ExcelCellStyle;
import utils.Utility;

@ViewScoped
@Named
public class PurchaseVoucherCardController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/***
	 * For Store
	 */
	private List<Stores> storeList = new ArrayList<Stores>();
	@Inject
	private StoresFacade storesFacade;

	/***
	 * Select aid w uireapet whek
	 */

	@Inject
	private ProvideAidFacade provideAidFacade;
	private List<SubmissionDetailsCfg> listSubmissionDetailsCfg = new ArrayList<>();

	/**
	 * for select desrved PErson
	 */

	@Inject
	private DeservedFacade deservedFacade;

	@Inject
	private HealthyStatusCfgFacade healthyStatusCfgFacade;

	@Inject
	private PoorPercenatageCfgFacade poorPercenatageCfgFacade;

	@Inject
	private ReligionReferenceCfgFacade religionReferenceCfgFacade;

	@Inject
	private InssuranceCfgFacade inssuranceCfgFacade;

	private List<HealthyStatusCfg> healthyStatusList = new ArrayList<>();
	private List<InssuranceCfg> inssuranceCfgList = new ArrayList<>();
	private List<ReligionReferenceCfg> religionReferenceCfgList = new ArrayList<>();
	private List<PoorPercentageCfg> poorPercentageCfgList = new ArrayList<>();

	private boolean hasCar = false;
	private boolean richLogical = false;
	private int nbChildFrom;
	private int nbChildTo;

	private HealthyStatusCfg healthyStatusCfg;
	private PoorPercentageCfg poorPercenatageCfg;
	private ReligionReferenceCfg religionReferenceCfg;
	private InssuranceCfg inssuranceCfg;

	// listResult ;
	private List<Deserved> desrvedList = new ArrayList<>();
	private List<Deserved> selectedListDeserved;

	private List<Deserved> desrvedListFilter;
	private List<Deserved> desrvedListFilterShowTable;

	private List<Deserved> orginalListFromDBGiven = new ArrayList<>();
	private String livingArea = null;
	private final String REQUEST_PARAM = "id";

	/***
	 * Item Unit page princapel
	 */

	private PurchaseVoucher purchaseVoucher = new PurchaseVoucher();

	@Inject
	private PurchaseVoucherFacade purchaseVoucherFacade;

	/***
	 * Print by user
	 */
	private ExcelOptions excelOpt;

	private PDFOptions pdfOpt;

	private boolean attachFileReady = false;

	private List<SubmissionDetailsCfg> listUsedSubbmissionUsed = new ArrayList<>();

	@PostConstruct
	public void init() {
		storeList = storesFacade.findAll();
		listSubmissionDetailsCfg = provideAidFacade.getAllSubmissionDetails();
		healthyStatusList = healthyStatusCfgFacade.findAll();
		inssuranceCfgList = inssuranceCfgFacade.findAll();
		religionReferenceCfgList = religionReferenceCfgFacade.findAll();
		poorPercentageCfgList = poorPercenatageCfgFacade.findAll();
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			if (!facesContext.isPostback()) {
				String id = externalContext.getRequestParameterMap().get(REQUEST_PARAM);
				if (id != null && Double.parseDouble(id) > 0) {
					purchaseVoucher = purchaseVoucherFacade.find(Long.parseLong(id));

					orginalListFromDBGiven = purchaseVoucher.getListDeserved();
					for (PurchaseVoucherCoupon purchaseVoucherCoupon : purchaseVoucher.getListPurchaseVoucherCoupon()) {
						listUsedSubbmissionUsed.add(purchaseVoucherCoupon.getSubmissionDetailsCfg());
						listSubmissionDetailsCfg.remove(purchaseVoucherCoupon.getSubmissionDetailsCfg());
					}

					if (purchaseVoucher == null) {
						try {
							Faces.redirect("exception");
						} catch (UncheckedIOException e) {
							e.printStackTrace();
						}
					}
					Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
					if (flash.containsKey("new-card")) {
						Utility.addSuccessMessage("save_success");
					}
				} else {
					purchaseVoucher = new PurchaseVoucher();
					purchaseVoucher.getListPurchaseVoucherCoupon().add(new PurchaseVoucherCoupon());

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Search for select Desrved
	 */

	public void findDesrved() {

		try {

			if (nbChildFrom > nbChildTo) {

				Utility.addErrorMessage("waring_nb_frmo_bigger_nb_to");

			} else {

				desrvedList = deservedFacade.findDesrvedsByList(hasCar, nbChildFrom, nbChildTo, healthyStatusCfg,
						poorPercenatageCfg, religionReferenceCfg, inssuranceCfg, richLogical, livingArea);

			}

			desrvedList.removeAll(orginalListFromDBGiven);

		} catch (Exception e) {
			e.printStackTrace();

			Utility.addErrorMessage("error_on_search");
		}

	}

	public void submit() {

		try {

			if (purchaseVoucher.getStores() != null) {

				int orginalListNumber = orginalListFromDBGiven.size();
				orginalListFromDBGiven.addAll(selectedListDeserved);
				purchaseVoucher.setListDeserved(orginalListFromDBGiven);

				double totalTokdimat = 0;

				for (PurchaseVoucherCoupon purchaseVoucherCoupon : purchaseVoucher.getListPurchaseVoucherCoupon()) {

					totalTokdimat += purchaseVoucherCoupon.getValue();

					if (Double.compare(purchaseVoucherCoupon.getValue() * selectedListDeserved.size(),
							purchaseVoucherCoupon.getSubmissionDetailsCfg().getBalance_ll()) > 0) {
						Utility.addWarningMessage(
								Utility.getMessage("You_do_not_have _this_balance to_submit", Utility.BUNDLE_FILE_NAME)
										+ " " + purchaseVoucherCoupon.getSubmissionDetailsCfg().getType());

						orginalListFromDBGiven = new ArrayList<>();
						return;
					}
				}

				purchaseVoucher.setTotalCost(totalTokdimat * (orginalListFromDBGiven.size()));
				purchaseVoucher = purchaseVoucherFacade.save2(purchaseVoucher, orginalListNumber);
				Utility.addSuccessMessage("save_success");
				Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
				flash.put("new-card", "true");
				changeUrl();
			} else {

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void changeUrl() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		String url = request.getRequestURL().toString();
		try {
			Faces.redirect(url + "?" + REQUEST_PARAM + "=%s", purchaseVoucher.getId() + "");

		} catch (UncheckedIOException e) {
			e.printStackTrace();
		}
	}

	public void addNewPurchaseVoucherCoupon() {
		purchaseVoucher.getListPurchaseVoucherCoupon().add(new PurchaseVoucherCoupon());
	}

	public void removePurchaseVoucherCoupon(PurchaseVoucherCoupon purchaseVoucherCoupon2) {

		try {
			PurchaseVoucherCoupon purchaseVoucherCoupon = new PurchaseVoucherCoupon();
			Iterator<PurchaseVoucherCoupon> listPurchaseVoucherCoupon = purchaseVoucher.getListPurchaseVoucherCoupon()
					.iterator();

			while (listPurchaseVoucherCoupon.hasNext()) {
				purchaseVoucherCoupon = (PurchaseVoucherCoupon) listPurchaseVoucherCoupon.next();

				if (purchaseVoucherCoupon.getSubmissionDetailsCfg() != null) {
					if (purchaseVoucherCoupon.getSubmissionDetailsCfg()
							.equals(purchaseVoucherCoupon2.getSubmissionDetailsCfg())) {
						purchaseVoucher.getListPurchaseVoucherCoupon().remove(purchaseVoucherCoupon);
						purchaseVoucher.setTotalCost(purchaseVoucher.getTotalCost()
								- purchaseVoucherCoupon.getValue() * orginalListFromDBGiven.size());

						purchaseVoucher.getListPurchaseVoucherCoupon().add(new PurchaseVoucherCoupon());

						purchaseVoucher = purchaseVoucherFacade.saveOnDeleteSubbimtiton(
								purchaseVoucherCoupon2.getSubmissionDetailsCfg(), purchaseVoucher,
								purchaseVoucherCoupon.getValue() * orginalListFromDBGiven.size());

						listUsedSubbmissionUsed.remove(purchaseVoucherCoupon.getSubmissionDetailsCfg());
						listSubmissionDetailsCfg.add(purchaseVoucherCoupon.getSubmissionDetailsCfg());

					}
				} else {
					purchaseVoucher.getListPurchaseVoucherCoupon().remove(purchaseVoucherCoupon2);

				}

				if (purchaseVoucher.getListPurchaseVoucherCoupon().isEmpty()) {
					purchaseVoucher.getListPurchaseVoucherCoupon().add(new PurchaseVoucherCoupon());

				}

				Utility.addSuccessMessage("delete_succes");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Stores> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Stores> storeList) {
		this.storeList = storeList;
	}

	public StoresFacade getStoresFacade() {
		return storesFacade;
	}

	public void setStoresFacade(StoresFacade storesFacade) {
		this.storesFacade = storesFacade;
	}

	public ProvideAidFacade getProvideAidFacade() {
		return provideAidFacade;
	}

	public void setProvideAidFacade(ProvideAidFacade provideAidFacade) {
		this.provideAidFacade = provideAidFacade;
	}

	public List<SubmissionDetailsCfg> getListSubmissionDetailsCfg() {
		return listSubmissionDetailsCfg;
	}

	public void setListSubmissionDetailsCfg(List<SubmissionDetailsCfg> listSubmissionDetailsCfg) {
		this.listSubmissionDetailsCfg = listSubmissionDetailsCfg;
	}

	public HealthyStatusCfgFacade getHealthyStatusCfgFacade() {
		return healthyStatusCfgFacade;
	}

	public void setHealthyStatusCfgFacade(HealthyStatusCfgFacade healthyStatusCfgFacade) {
		this.healthyStatusCfgFacade = healthyStatusCfgFacade;
	}

	public PoorPercenatageCfgFacade getPoorPercenatageCfgFacade() {
		return poorPercenatageCfgFacade;
	}

	public void setPoorPercenatageCfgFacade(PoorPercenatageCfgFacade poorPercenatageCfgFacade) {
		this.poorPercenatageCfgFacade = poorPercenatageCfgFacade;
	}

	public ReligionReferenceCfgFacade getReligionReferenceCfgFacade() {
		return religionReferenceCfgFacade;
	}

	public void setReligionReferenceCfgFacade(ReligionReferenceCfgFacade religionReferenceCfgFacade) {
		this.religionReferenceCfgFacade = religionReferenceCfgFacade;
	}

	public InssuranceCfgFacade getInssuranceCfgFacade() {
		return inssuranceCfgFacade;
	}

	public void setInssuranceCfgFacade(InssuranceCfgFacade inssuranceCfgFacade) {
		this.inssuranceCfgFacade = inssuranceCfgFacade;
	}

	public List<HealthyStatusCfg> getHealthyStatusList() {
		return healthyStatusList;
	}

	public void setHealthyStatusList(List<HealthyStatusCfg> healthyStatusList) {
		this.healthyStatusList = healthyStatusList;
	}

	public List<InssuranceCfg> getInssuranceCfgList() {
		return inssuranceCfgList;
	}

	public void setInssuranceCfgList(List<InssuranceCfg> inssuranceCfgList) {
		this.inssuranceCfgList = inssuranceCfgList;
	}

	public List<ReligionReferenceCfg> getReligionReferenceCfgList() {
		return religionReferenceCfgList;
	}

	public void setReligionReferenceCfgList(List<ReligionReferenceCfg> religionReferenceCfgList) {
		this.religionReferenceCfgList = religionReferenceCfgList;
	}

	public List<PoorPercentageCfg> getPoorPercentageCfgList() {
		return poorPercentageCfgList;
	}

	public void setPoorPercentageCfgList(List<PoorPercentageCfg> poorPercentageCfgList) {
		this.poorPercentageCfgList = poorPercentageCfgList;
	}

	public boolean isHasCar() {
		return hasCar;
	}

	public void setHasCar(boolean hasCar) {
		this.hasCar = hasCar;
	}

	public boolean isRichLogical() {
		return richLogical;
	}

	public void setRichLogical(boolean richLogical) {
		this.richLogical = richLogical;
	}

	public int getNbChildFrom() {
		return nbChildFrom;
	}

	public void setNbChildFrom(int nbChildFrom) {
		this.nbChildFrom = nbChildFrom;
	}

	public int getNbChildTo() {
		return nbChildTo;
	}

	public void setNbChildTo(int nbChildTo) {
		this.nbChildTo = nbChildTo;
	}

	public HealthyStatusCfg getHealthyStatusCfg() {
		return healthyStatusCfg;
	}

	public void setHealthyStatusCfg(HealthyStatusCfg healthyStatusCfg) {
		this.healthyStatusCfg = healthyStatusCfg;
	}

	public PoorPercentageCfg getPoorPercenatageCfg() {
		return poorPercenatageCfg;
	}

	public void setPoorPercenatageCfg(PoorPercentageCfg poorPercenatageCfg) {
		this.poorPercenatageCfg = poorPercenatageCfg;
	}

	public ReligionReferenceCfg getReligionReferenceCfg() {
		return religionReferenceCfg;
	}

	public void setReligionReferenceCfg(ReligionReferenceCfg religionReferenceCfg) {
		this.religionReferenceCfg = religionReferenceCfg;
	}

	public InssuranceCfg getInssuranceCfg() {
		return inssuranceCfg;
	}

	public void setInssuranceCfg(InssuranceCfg inssuranceCfg) {
		this.inssuranceCfg = inssuranceCfg;
	}

	public List<Deserved> getDesrvedList() {
		return desrvedList;
	}

	public void setDesrvedList(List<Deserved> desrvedList) {
		this.desrvedList = desrvedList;
	}

	public List<Deserved> getDesrvedListFilter() {
		return desrvedListFilter;
	}

	public void setDesrvedListFilter(List<Deserved> desrvedListFilter) {
		this.desrvedListFilter = desrvedListFilter;
	}

	public String getLivingArea() {
		return livingArea;
	}

	public void setLivingArea(String livingArea) {
		this.livingArea = livingArea;
	}

	public List<Deserved> getSelectedListDeserved() {
		return selectedListDeserved;
	}

	public void setSelectedListDeserved(List<Deserved> selectedListDeserved) {
		this.selectedListDeserved = selectedListDeserved;
	}

	public PurchaseVoucher getPurchaseVoucher() {
		return purchaseVoucher;
	}

	public void setPurchaseVoucher(PurchaseVoucher purchaseVoucher) {
		this.purchaseVoucher = purchaseVoucher;
	}

	public List<Deserved> getOrginalListFromDBGiven() {
		return orginalListFromDBGiven;
	}

	public void setOrginalListFromDBGiven(List<Deserved> orginalListFromDBGiven) {
		this.orginalListFromDBGiven = orginalListFromDBGiven;
	}

	public List<Deserved> getDesrvedListFilterShowTable() {
		return desrvedListFilterShowTable;
	}

	public void setDesrvedListFilterShowTable(List<Deserved> desrvedListFilterShowTable) {
		this.desrvedListFilterShowTable = desrvedListFilterShowTable;
	}

	public void preProcessPDF_ext(Object document) throws Exception {
		try {
			String path = "http://localhost:8080/ShariaFunds/faces/resources/fonts/arabic/arial.ttf";
			FontFactory.register(path);
			if (!FontFactory.isRegistered("arial")) {
				FontFactory.register(path);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			preProcessPDF(document);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.setPageSize(PageSize.A4);
		pdf.open();

		// add a couple of blank lines
		// pdf.add(Chunk.NEWLINE); Line FAde
		Font fontbold = FontFactory.getFont("arial", 16, Font.BOLD);
		fontbold.setColor(55, 55, 55);

		pdf.add(new Paragraph("تصهثخيتصث", fontbold));
		// add a couple of blank lines
		pdf.add(Chunk.NEWLINE);

	}

	public void postProcessXLS(Object document) {
		ExcelCellStyle excelCellStyle=new ExcelCellStyle();
		
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		header.setHeight((short) 400);
		CellStyle headerCellStyle =  excelCellStyle.getColumnTitleCellStyle(wb);
		 
		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			Cell cell = header.getCell(i);
			cell.setCellStyle(headerCellStyle);
			 
		}

		HSSFRow rowTotalForEachSubmission = wb.getSheetAt(0).createRow(orginalListFromDBGiven.size() + 2);
		sheet.addMergedRegion(new CellRangeAddress(orginalListFromDBGiven.size() + 2, orginalListFromDBGiven.size() + 2,
				header.getPhysicalNumberOfCells() - 2, header.getPhysicalNumberOfCells() - 1));
		HSSFCell sCellTittle = rowTotalForEachSubmission.createCell(header.getPhysicalNumberOfCells() - 2);

		/**
		 * lal majmo3
		 */

		sCellTittle.setCellValue("مجموع التقديم :");
		sCellTittle.setCellStyle(headerCellStyle);
//		int counterRow = 1;
		// add el tokdimet + ade imeta

		/**
		 * lal t2dimet li fo2
		 * 
		 */
		CellStyle cellStyle2 = excelCellStyle.getColumnTitleCellStyle(wb);

		int nbOfTokdmit = 0;
		for (PurchaseVoucherCoupon var : purchaseVoucher.getListPurchaseVoucherCoupon()) {
			HSSFRow newRow = wb.getSheetAt(0).getRow(0);

			HSSFCell x = newRow.createCell(header.getPhysicalNumberOfCells());

			x.setCellStyle(cellStyle2);
			x.setCellValue(var.getSubmissionDetailsCfg().getType());
			nbOfTokdmit++;

			for (int i = 1; i <= orginalListFromDBGiven.size(); i++) {
				HSSFRow sRow = wb.getSheetAt(0).getRow(i);

				HSSFCell sCell = sRow.createCell(header.getPhysicalNumberOfCells() - 1);
				sCell.setCellStyle(excelCellStyle.myCellStyle(wb));
				sCell.setCellValue(var.getValue());
				 
			}

			HSSFCell sCell = rowTotalForEachSubmission.createCell(header.getPhysicalNumberOfCells() - 1);

			sCell.setCellValue(var.getValue() * orginalListFromDBGiven.size());
			sCell.setCellStyle(excelCellStyle.myCellStyle(wb));
		}

		
		// hon la hot el total submissions
		CellStyle cellStyle4 =excelCellStyle.getColumnTitleCellStyle(wb);
		
		HSSFRow newRow = wb.getSheetAt(0).createRow(orginalListFromDBGiven.size() + 4);
		HSSFCell x = newRow.createCell(header.getPhysicalNumberOfCells() - 3 - nbOfTokdmit + 1);
		sheet.addMergedRegion(new CellRangeAddress(orginalListFromDBGiven.size() + 4, orginalListFromDBGiven.size() + 4,
				header.getPhysicalNumberOfCells() - 3 - nbOfTokdmit + 1,
				header.getPhysicalNumberOfCells() - 2 - nbOfTokdmit + 1));
		x.setCellValue(Utility.getMessage("total_purcahse_value", Utility.BUNDLE_FILE_NAME));
		x.setCellStyle(cellStyle4);
		
		HSSFCell x2 = newRow.createCell(header.getPhysicalNumberOfCells() - nbOfTokdmit);
		x2.setCellValue(purchaseVoucher.getTotalCost());
		x2.setCellStyle(excelCellStyle.myCellStyle(wb));
		 
		// hon la hot store name
		HSSFRow newRowStore = wb.getSheetAt(0).createRow(orginalListFromDBGiven.size() + 5);
		HSSFCell storeDefintion = newRowStore.createCell(header.getPhysicalNumberOfCells() - 3 - nbOfTokdmit + 1);
		sheet.addMergedRegion(new CellRangeAddress(orginalListFromDBGiven.size() + 5, orginalListFromDBGiven.size() + 5,
				header.getPhysicalNumberOfCells() - 3 - nbOfTokdmit + 1,
				header.getPhysicalNumberOfCells() - 2 - nbOfTokdmit + 1));
		storeDefintion.setCellValue(Utility.getMessage("stores_name", Utility.BUNDLE_FILE_NAME));
		storeDefintion.setCellStyle(cellStyle4);
		
		HSSFCell storeName = newRowStore.createCell(header.getPhysicalNumberOfCells() - nbOfTokdmit);
		storeName.setCellValue(purchaseVoucher.getStores().getStoreName());
		storeName.setCellStyle(excelCellStyle.myCellStyle(wb));
		
		sheet.setRightToLeft(true);	
		sheet.setFitToPage(true);
		sheet.getPrintSetup().setFitHeight((short) 0);
		sheet.getPrintSetup().setFitWidth((short) 1);
		sheet.setPrintGridlines(false);
		sheet.setAutobreaks(true);
	    sheet.setDefaultColumnWidth(20);
	    
	}

	public void payBill() {

		try {

			if (!attachFileReady) {

				Utility.addWarningMessage("you_need_attach_file");
				return;
			}

			purchaseVoucher.setPaied(true);
			purchaseVoucher = purchaseVoucherFacade.save(purchaseVoucher);

			Utility.addSuccessMessage("save_success");
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.put("new-card", "true");
			changeUrl();
			attachFileReady = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void handleOtherFileUpload(FileUploadEvent event) {
		try {
			UploadedFile uploadedFile = event.getFile();
			Attachment attachment = new Attachment();
			attachment.setFileName(uploadedFile.getFileName());
			attachment.setSize(uploadedFile.getSize());
			attachment.setContentType(uploadedFile.getContentType());
			attachment.setData(uploadedFile.getContent());
			this.purchaseVoucher.addToAttachment(attachment);
			Utility.addSuccessMessage("related_file_added");
			attachFileReady = true;
		} catch (Exception e) {
			Utility.addErrorMessage("error_file_added");
			e.printStackTrace();
		}
	}

	public ExcelOptions getExcelOpt() {
		return excelOpt;
	}

	public void setExcelOpt(ExcelOptions excelOpt) {
		this.excelOpt = excelOpt;
	}

	public PDFOptions getPdfOpt() {
		return pdfOpt;
	}

	public void setPdfOpt(PDFOptions pdfOpt) {
		this.pdfOpt = pdfOpt;
	}

	public boolean isAttachFileReady() {
		return attachFileReady;
	}

	public void setAttachFileReady(boolean attachFileReady) {
		this.attachFileReady = attachFileReady;
	}

	public List<SubmissionDetailsCfg> getListUsedSubbmissionUsed() {
		return listUsedSubbmissionUsed;
	}

	public void setListUsedSubbmissionUsed(List<SubmissionDetailsCfg> listUsedSubbmissionUsed) {
		this.listUsedSubbmissionUsed = listUsedSubbmissionUsed;
	}

}
