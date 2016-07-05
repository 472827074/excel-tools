package com.zhibitech.easyreport.tools.exceltool.impt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhibitech.easyreport.tools.exceltool.validate.ValidateResult;
import com.zhibitech.framework.core.utils.StringUtils;

/**
 * 
 * @author YuMeng 错误分析表相关
 */
public class ErrorExcelHandle {

	private InputStream inputStream;
	private File storedFile;// 保存文件路径
	private HSSFPatriarch drawing;// 操作EXCEL表的容器
	private HSSFWorkbook workbook;// 工作薄
	private HSSFSheet sheet;// excel sheet数据；
	private HSSFCellStyle style;// 表的样式
	private String[][] totalDatas;// 导入表格的所有数据
	private FileOutputStream fOut;
	private String errorFileUrl;// 错误数据表路径
	private String errorFileDownloadUrl;// 错误数据表下载路径

	public final static Logger logger = LoggerFactory.getLogger(ErrorExcelHandle.class);

	// 创建错误表
	public String createErrorDataExcel(String fileName) {
		String tempName = fileName;
		try {
			String baseTempUrl = "";
			inputStream = new java.io.FileInputStream(baseTempUrl + File.separator + tempName);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			File file = createStoreFile(fileName);

			FileOutputStream fileOutputStream = new FileOutputStream(file);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

			int i = -1;
			while ((i = bufferedInputStream.read()) != -1) {
				bufferedOutputStream.write(i);
			}

			bufferedInputStream.close();
			inputStream.close();

			bufferedOutputStream.close();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("文件打开失败!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("文件打开失败!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorFileUrl;
	}

	// 创建表的引用
	public void createExcelCite() throws Exception {
		this.workbook = new HSSFWorkbook(new FileInputStream(new File(errorFileUrl)));
		this.sheet = workbook.getSheetAt(0);
		this.style = getStyle(workbook);
		this.drawing = sheet.createDrawingPatriarch();
	}

	// 自动填充错误信息中填报单位的信息
	public void fullUnit(String[][] totalDatas) {
		this.setTotalDatas(totalDatas);
		HSSFRow row = sheet.getRow(3);
		HSSFCell cell;
		for (int i = 1; i < totalDatas[3].length; i++) {
			cell = row.getCell(i);
			if (StringUtils.isEmpty(totalDatas[3][i])) {
				HSSFCellStyle cellStyle = cell.getCellStyle();
				// 设置单元格的值

				cell.setCellValue(totalDatas[3][i]);
				cell.setCellStyle(cellStyle);
			}

		}

		row = sheet.getRow(0);
		cell = row.getCell(0);
		cell.setCellComment(cell.getCellComment());

	}

	// 自动填充用户错误信息中填报单位的信息
	public void fullUserUnit(String[][] totalDatas) {
		this.setTotalDatas(totalDatas);
		HSSFRow row = sheet.createRow(1);
		HSSFCell cell;
		for (int i = 0; i < totalDatas[1].length; i++) {
			cell = row.createCell(i);

			// 设置单元格的值

			if (i % 2 == 0 && i != 0 && totalDatas[1][i].length() > 0) {
				HSSFRichTextString ts = new HSSFRichTextString(totalDatas[1][i]);
				ts.applyFont(0, totalDatas[1][i].length() - 1, getFontNotColorStyle(workbook));
				if (totalDatas[1][i].indexOf("*") != -1) {
					ts.applyFont(totalDatas[1][i].indexOf("*"), totalDatas[1][i].length(), getFontColorStyle(workbook));
				}

				cell.setCellValue(ts);
				cell.setCellStyle(getCenterStyle(workbook));

			} else {
				cell.setCellValue(totalDatas[1][i]);
				cell.setCellStyle(getFontStyle(workbook));
			}
		}
	}

	// 将单条错误信息加入到表中
	public void addErrorMessage(ValidateResult result, String[] rowData, int currentRow, String dataType,
			int checkOrgOrNot) {
		try {
			addNewErrorRow(result, rowData, workbook, sheet, style, currentRow);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("文件不存在!");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("文件不存在!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void outFile(int currentDealRow, int importDataNum) throws Exception {
		fOut = new FileOutputStream(new File(errorFileUrl));
		workbook.write(fOut);
		fOut.flush();
		fOut.close();

	}

	private void addNewErrorRow(ValidateResult result, String[] rowData, HSSFWorkbook workbook, HSSFSheet sheet,
			HSSFCellStyle style, int currentRow) throws Exception {
		HSSFRow row = sheet.createRow(currentRow);
		HSSFCell cell;
		for (int i = 0; i < rowData.length; i++) {
			cell = row.createCell(i);
			// 设置单元格的值
			cell.setCellValue(rowData[i]);
		}
		Comment commentnew;
		Map<String, String> maps = result.getMapMessages();
		for (String key : maps.keySet()) {
			commentnew = getComment(workbook, sheet, maps.get(key));
			cell = row.createCell(Integer.parseInt(key));
			cell.setCellValue(rowData[Integer.parseInt(key)]);
			cell.setCellStyle(style);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格格式为文本格式
			cell.setCellComment(commentnew);
		}

	}

	// 添加批注
	private Comment getComment(HSSFWorkbook workbook, HSSFSheet sheet, String info) {

		HSSFComment comment = drawing.createComment(new HSSFClientAnchor(100, 0, 0, 0, (short) 1, 1, (short) 6, 8));
		// 输入批注信息
		comment.setString(new HSSFRichTextString(info));
		comment.setAuthor("Apache POI");
		return comment;
	}

	// 设置表格字体颜色
	private HSSFCellStyle getFontStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);// 字号
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//  居中 
		return style;
	}

	// 设置单元格字居中
	private HSSFCellStyle getCenterStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//  居中 
		return style;
	}

	// 设置字体颜色
	private HSSFFont getFontColorStyle(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);// 字号
		font.setColor(HSSFColor.RED.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
		return font;
	}

	// 设置字体不设颜色
	private HSSFFont getFontNotColorStyle(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);// 字号
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		return font;
	}

	// 设置表格单元背景颜色
	private HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.RED.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));// 设置CELL格式

		return style;
	}

	private File createStoreFile(String extendname) throws Exception {
		Calendar calCurrent = Calendar.getInstance();
		int intMillisecond = calCurrent.get(Calendar.MILLISECOND);
		int intMinute = calCurrent.get(Calendar.MINUTE);
		int intHour = calCurrent.get(Calendar.HOUR_OF_DAY);
		int intDay = calCurrent.get(Calendar.DATE);
		int intMonth = calCurrent.get(Calendar.MONTH) + 1;
		int intYear = calCurrent.get(Calendar.YEAR);
		String errorFile = String.valueOf(intYear) + intMonth + intDay + intHour + intMinute + intMillisecond + "-"
				+ extendname;
		storedFile = new File(AttachmentCfg.excel_upload_dir + File.separator + errorFile);
		if (!storedFile.getParentFile().isDirectory()) {
			storedFile.getParentFile().mkdirs();
		}
		if (!storedFile.exists()) {
			storedFile.createNewFile();
		}
		setErrorFileUrl(AttachmentCfg.excel_upload_dir + File.separator + errorFile);
		return storedFile;
	}

	public String getErrorFileUrl() {
		return errorFileUrl;
	}

	public void setErrorFileUrl(String errorFileUrl) {
		this.errorFileUrl = errorFileUrl;
	}

	public String[][] getTotalDatas() {
		return totalDatas;
	}

	public void setTotalDatas(String[][] totalDatas) {
		this.totalDatas = totalDatas;
	}

	public String getErrorFileDownloadUrl() {
		return errorFileDownloadUrl;
	}

	public void setErrorFileDownloadUrl(String errorFileDownloadUrl) {
		this.errorFileDownloadUrl = errorFileDownloadUrl;
	}
}
