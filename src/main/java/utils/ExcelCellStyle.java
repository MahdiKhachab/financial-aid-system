package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelCellStyle {

	public CellStyle getHeaderCellStyle(Workbook wb) {
		CellStyle headerCellStyle = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 14);
		font.setBold(true);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFont(font);
		headerCellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		 
		return headerCellStyle;
	}

	public CellStyle getColumnTitleCellStyle(Workbook wb) {
		CellStyle ColumnTitleCellStyle = wb.createCellStyle();
		Font fontTitle = wb.createFont();
		fontTitle.setFontHeightInPoints((short) 11);
		fontTitle.setBold(true);

		ColumnTitleCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

		ColumnTitleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		short blackColor = IndexedColors.BLACK.getIndex();

		ColumnTitleCellStyle.setFont(fontTitle);
		ColumnTitleCellStyle.setBorderBottom(BorderStyle.valueOf((short) 1));
		ColumnTitleCellStyle.setBorderTop(BorderStyle.valueOf((short) 1));
		ColumnTitleCellStyle.setBorderLeft(BorderStyle.valueOf((short) 1));
		ColumnTitleCellStyle.setBorderRight(BorderStyle.valueOf((short) 1));
		ColumnTitleCellStyle.setTopBorderColor(blackColor);
		ColumnTitleCellStyle.setRightBorderColor(blackColor);
		ColumnTitleCellStyle.setLeftBorderColor(blackColor);
		ColumnTitleCellStyle.setTopBorderColor(blackColor);
		ColumnTitleCellStyle.setWrapText(true);
		ColumnTitleCellStyle.setAlignment(HorizontalAlignment.CENTER);
		ColumnTitleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		return ColumnTitleCellStyle;
	}
	
	public CellStyle myCellStyle(Workbook wb) {
		Font fontTitle = wb.createFont();
		fontTitle.setFontHeightInPoints((short) 10);
		CellStyle myCellStyle = wb.createCellStyle();
		//myCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		
		short blackColor = IndexedColors.BLACK.getIndex();
		myCellStyle.setBorderBottom(BorderStyle.valueOf((short) 1));
		myCellStyle.setBorderTop(BorderStyle.valueOf((short) 1));
		myCellStyle.setBorderLeft(BorderStyle.valueOf((short) 1));
		myCellStyle.setBorderRight(BorderStyle.valueOf((short) 1));
		myCellStyle.setTopBorderColor(blackColor);
		myCellStyle.setRightBorderColor(blackColor);
		myCellStyle.setLeftBorderColor(blackColor);
		myCellStyle.setTopBorderColor(blackColor);
		myCellStyle.setWrapText(true);
		myCellStyle.setAlignment(HorizontalAlignment.CENTER);
		myCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		return myCellStyle;
	}

	public CellStyle getRowCellStyle(Workbook wb) {
		short blackColor = IndexedColors.BLACK.getIndex();
		CellStyle rowCellStyle = wb.createCellStyle();
		Font fontTitle = wb.createFont();
		fontTitle.setFontHeightInPoints((short) 11);
		rowCellStyle.setFont(fontTitle);
		rowCellStyle.setBorderBottom(BorderStyle.valueOf((short) 1));
		rowCellStyle.setBorderTop(BorderStyle.valueOf((short) 1));
		rowCellStyle.setBorderLeft(BorderStyle.valueOf((short) 1));
		rowCellStyle.setBorderRight(BorderStyle.valueOf((short) 1));
		rowCellStyle.setTopBorderColor(blackColor);
		rowCellStyle.setRightBorderColor(blackColor);
		rowCellStyle.setLeftBorderColor(blackColor);
		rowCellStyle.setTopBorderColor(blackColor);
		rowCellStyle.setWrapText(true);
		rowCellStyle.setAlignment(HorizontalAlignment.CENTER);
		rowCellStyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
		 
		return rowCellStyle;
	}

	public void createCellsHeader(Workbook wb, String title, int dateNumCell) {
		CellStyle style = getHeaderCellStyle(wb);
		Sheet sheet = wb.getSheetAt(0);
		Row row0 = sheet.createRow(0);
		Row row1 = sheet.createRow(2);
		System.out.println(row1.getLastCellNum());
		Cell cell1 = row0.createCell(getColumnsNb(sheet) / 2);
		Cell cell2 = row0.createCell(getColumnsNb(sheet) - 1);
		cell1.setCellValue("ط¨ط§ط³ظ…ظ‡ طھط¹ط§ظ„ظ‰");
		cell2.setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		cell1.setCellStyle(style);
		cell2.setCellStyle(style);

		Cell cell01 = row1.createCell(0);
		cell01.setCellValue(title);
		cell01.setCellStyle(style);

	}

	public int getColumnsNb(Sheet sheet) {
		return sheet.getRow(4).getPhysicalNumberOfCells();
	}

}
