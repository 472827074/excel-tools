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
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zhibitech.easyreport.tools.exceltool.validate.ValidateResult;


/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2016年7月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ErrorExcelHandle {

	private Drawing drawing;// 操作EXCEL表的容器
	
	private Workbook workbook;// 工作薄
	//private HSSFSheet sheet;// excel sheet数据；
	private Sheet sheet;
	
	private CellStyle cellStyle;
	
	private HSSFCellStyle style;// 表的样式

	String baseTempUrl = "E:\\workspace\\easy-report\\easy-report-exceltool\\template";
	
	public ErrorExcelHandle(String fileName) throws Exception {
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		if(prefix.toLowerCase().equals("xls")){
			this.workbook = new HSSFWorkbook(new FileInputStream(new File(baseTempUrl+"\\"+fileName)));
			
		}else{
			this.workbook = new XSSFWorkbook(new FileInputStream(new File(baseTempUrl+"\\"+fileName)));
		}
		this.sheet = workbook.getSheetAt(0);
		this.cellStyle = getStyle(workbook);
		this.drawing = sheet.createDrawingPatriarch();
		createErrorDataExcel(fileName);
	}

	// 创建错误表
	@SuppressWarnings("resource")
	private void createErrorDataExcel(String fileName) {
		try {
			FileInputStream inputStream = new FileInputStream(baseTempUrl + File.separator + fileName);

			FileChannel inChanel = inputStream.getChannel();

			File file = new File(baseTempUrl + File.separator+"temp.xlsx");

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
			cell.setCellStyle(style);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置单元格格式为文本格式
			cell.setCellComment(commentnew);
		}

	}

	public void outFile() throws Exception {
		FileOutputStream fOut = new FileOutputStream(new File(""));
		workbook.write(fOut);
		fOut.flush();
		fOut.close();

	}

	// 添加批注
	private Comment getComment(String info) {

		Comment comment = drawing.createCellComment(new HSSFClientAnchor(100, 0, 0, 0, (short) 1, 1, (short) 6, 8));
		// 输入批注信息
		comment.setString(new HSSFRichTextString(info));
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
