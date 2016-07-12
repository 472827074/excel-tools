package com.zhibitech.easyreport.tools.exceltool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zhibitech.easyreport.tools.exceltool.validate.ValidateResult;


/**
 * excel错误数据分析处理
 * 
 * @author  yumeng
 * @version  [版本号, 2016年7月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ErrorExcelHandle {

	private Drawing drawing;// 操作EXCEL表的容器
	
	private Workbook workbook;// 工作薄

	private Sheet sheet;
	
	private CellStyle cellStyle;

	private  String templateUrl = System.getProperty("user.dir")+File.separator+"template";
	
	private String excelType;
	
	public ErrorExcelHandle(String fileName) throws Exception {
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		this.excelType = prefix;
		if(excelType.toLowerCase().equals("xls")){
			this.workbook = new HSSFWorkbook(new FileInputStream(new File(templateUrl+File.separator+fileName)));
			
		}else{
			this.workbook = new XSSFWorkbook(new FileInputStream(new File(templateUrl+File.separator+fileName)));
		}
		this.sheet = workbook.getSheetAt(0);
		this.cellStyle = getStyle(workbook);
		this.drawing = sheet.createDrawingPatriarch();
		createErrorDataExcel(fileName);
	}

	// 创建错误表
	@SuppressWarnings("resource")
	private void createErrorDataExcel(String fileName)  {
		try {
			FileChannel inChanel = new FileInputStream(templateUrl + File.separator + fileName).getChannel();
			File file = new File(templateUrl + File.separator+"temp.xlsx");
			FileChannel outChannel =  new FileOutputStream(file).getChannel();
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


	public void addErrorMessage(ValidateResult result, String[] rowData, int currentRow) {
		Row row = sheet.createRow(currentRow);
		Cell cell;
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
			cell.setCellStyle(cellStyle);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格格式为文本格式
			cell.setCellComment(commentnew);
		}

	}

	public void outFile() throws Exception {
		FileOutputStream fOut = new FileOutputStream(new File(templateUrl + File.separator+"temp.xlsx"));
		workbook.write(fOut);
		fOut.flush();
		fOut.close();

	}

	// 添加批注
	private Comment getComment(String info) {

		ClientAnchor clientAnchor = null;
		RichTextString richTextString = null;
		if(this.excelType.equals("xls")){
			richTextString = new HSSFRichTextString(info);
			clientAnchor = new HSSFClientAnchor(100, 0, 0, 0, (short) 1, 1, (short) 6, 8);
		}else{
			richTextString = new XSSFRichTextString(info);
			clientAnchor = new XSSFClientAnchor(100, 0, 0, 0, (short) 1, 1, (short) 6, 8);
			
		}
		Comment comment = drawing.createCellComment(clientAnchor);
		// 输入批注信息
		comment.setString(richTextString);
		comment.setAuthor("Apache POI");
		return comment;
	}



	// 设置表格单元背景颜色
	private CellStyle getStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.RED.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));// 设置CELL格式

		return style;
	}

	
}
