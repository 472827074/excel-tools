package com.zhibitech.easyreport.tools.exceltool.convert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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

/**
 * 
 * @author YuMeng 错误分析表相关
 */
public class ErrorExcelHandle {

	private HSSFPatriarch drawing;// 操作EXCEL表的容器
	private HSSFWorkbook workbook;// 工作薄
	private HSSFSheet sheet;// excel sheet数据；
	private HSSFCellStyle style;// 表的样式

	public final static Logger logger = LoggerFactory.getLogger(ErrorExcelHandle.class);

	public ErrorExcelHandle(String fileName) throws Exception {
		this.workbook = new HSSFWorkbook(new FileInputStream(new File(fileName)));
		this.sheet = workbook.getSheetAt(0);
		this.style = getStyle(workbook);
		this.drawing = sheet.createDrawingPatriarch();
		createErrorDataExcel(fileName);
	}

	// 创建错误表
	@SuppressWarnings("resource")
	private void createErrorDataExcel(String fileName) {
		String tempName = fileName;
		try {
			String baseTempUrl = "";
			FileInputStream inputStream = new FileInputStream(baseTempUrl + File.separator + tempName);

			FileChannel inChanel = inputStream.getChannel();

			File file = new File("错误分析表文件");

			FileOutputStream fileOutputStream = new FileOutputStream(file);

			FileChannel outChannel = fileOutputStream.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (true) {
				buffer.clear();
				int r = inChanel.read(buffer);
				if (r == -1) {
					break;
				}
				buffer.flip();
				outChannel.write(buffer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 将单条错误信息加入到表中
	public void addErrorMessage(ValidateResult result, String[] rowData, int currentRow) {
		HSSFRow row = sheet.createRow(currentRow);
		HSSFCell cell;
		for (int i = 0; i < rowData.length; i++) {
			cell = row.createCell(i);
			// 设置单元格的值
			cell.setCellValue(rowData[i]);
		}
		Comment commentnew;
		Map<Integer, String> maps = result.getMessages();
		for (Integer key : maps.keySet()) {
			commentnew = getComment(maps.get(key));
			cell = row.createCell(key);
			cell.setCellValue(rowData[key]);
			cell.setCellStyle(style);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格格式为文本格式
			cell.setCellComment(commentnew);
		}

	}

	public void outFile(int currentDealRow, int importDataNum) throws Exception {
		FileOutputStream fOut = new FileOutputStream(new File(""));
		workbook.write(fOut);
		fOut.flush();
		fOut.close();

	}

	// 添加批注
	private Comment getComment(String info) {

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

	// 设置表格单元背景颜色
	private HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.RED.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));// 设置CELL格式

		return style;
	}

	
}
