package com.zhibitech.easyreport.tools.exceltool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 默认提供的excel数据读取实现
 * 
 * @author yumeng
 * @version [版本号, 2016年6月29日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DefaultExcelReader implements ExcelReader {

	public ExcelData readFile(File file) throws IOException {

		if (file == null) {
			throw new IOException("文件未找到");
		}
		String fileName = file.getName();
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		ExcelData data = new ExcelData();
		if (ExcelReader.EXCEL_PREFIX_XLS.equals(prefix.toLowerCase())) {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFEventFactory factory = new HSSFEventFactory();
			HSSFRequest request = new HSSFRequest();
			request.addListener(new BOFRecordListener(data), BOFRecord.sid);
			SSTRecordListener stringListener = new SSTRecordListener(data);
			request.addListener(stringListener, SSTRecord.sid);
			request.addListener(stringListener, LabelSSTRecord.sid);
			NumbericListener numberListener = new NumbericListener(data);
			FormatTrackingHSSFListener fl = new FormatTrackingHSSFListener(numberListener);
			numberListener.setFormatTrackingHSSFListener(fl);
			request.addListenerForAllRecords(fl);
			factory.processWorkbookEvents(request, fs);
		} else if (ExcelReader.EXCEL_PREFIX_XLSX.equals(prefix.toLowerCase())) {
			InputStream is = new FileInputStream(file);
			@SuppressWarnings("resource")
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			// 读取sheet;
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// 创建一个sheet数据记录
				data.addWorkSheet();
				// 读取row
				for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						short cellSize = xssfRow.getLastCellNum();
						for (short i = 0; i < cellSize; i++) {
							XSSFCell no = xssfRow.getCell(i);
							if(no != null){
								no.setCellType(Cell.CELL_TYPE_STRING);
								data.addString(rowNum, i, no.getStringCellValue());
							}
							
						}

					}
				}
			}

		} else {
			throw new IOException("不是excel数据");
		}
		return data;
	}

	public static void main(String args[]) {
		try {
			ExcelReader reader = new DefaultExcelReader();
			String projectDir = System.getProperty("user.dir");
			System.out.println(projectDir);

			ExcelData data = reader.readFile(new File(projectDir + "\\template\\user.xlsx"));
			System.out.println(data.getSheetDatas(0)[0][0]);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class BOFRecordListener implements HSSFListener {
	private ExcelData data;

	BOFRecordListener(ExcelData data) {
		this.data = data;
	}

	public void processRecord(Record record) {
		if (BOFRecord.sid == record.getSid()) {
			BOFRecord bofRecord = (BOFRecord) record;
			if (BOFRecord.TYPE_WORKBOOK == bofRecord.getType()) {
				// 工作簿开始
			} else if (BOFRecord.TYPE_WORKSHEET == bofRecord.getType()) {
				// 工作表开始
				data.addWorkSheet();
			}
		}
	}
}

class SSTRecordListener implements HSSFListener {
	private SSTRecord sstRecord;
	private ExcelData data;

	SSTRecordListener(ExcelData data) {
		this.data = data;
	}

	public void processRecord(Record record) {
		if (record.getSid() == SSTRecord.sid) {
			// 发现SST，表明xls文件中有文本存在，SST保存xls文件中唯一的String，各个String都是对应着SST记录的索引
			sstRecord = (SSTRecord) record;
		} else if (LabelSSTRecord.sid == record.getSid()) {
			LabelSSTRecord lsrec = (LabelSSTRecord) record;
			data.addString(lsrec.getRow(), lsrec.getColumn(), sstRecord.getString(lsrec.getSSTIndex()).toString());
		}
	}
}

class NumbericListener implements HSSFListener {
	private FormatTrackingHSSFListener formatListener;
	private ExcelData data;

	NumbericListener(ExcelData data) {
		this.data = data;
	}

	@Override
	public void processRecord(Record record) {
		String stringValue = "";
		if (record.getSid() == FormulaRecord.sid) {
			FormulaRecord frecord = (FormulaRecord) record;
			if (Double.isNaN(frecord.getValue())) {
				stringValue = String.valueOf(frecord.getValue()).trim();
			} else {
				stringValue = formatListener.formatNumberDateCell(frecord).trim();
			}
			if (stringValue != null) {
				while (stringValue.length() > 0 && stringValue.endsWith("_")) {
					stringValue = stringValue.substring(0, stringValue.length() - 1);
				}
			}
			data.addString(frecord.getRow(), frecord.getColumn(), stringValue);
		} else if (record.getSid() == NumberRecord.sid) {
			NumberRecord nRecord = (NumberRecord) record;
			stringValue = formatListener.formatNumberDateCell(nRecord).trim();
			if (stringValue != null) {
				while (stringValue.length() > 0 && stringValue.endsWith("_")) {
					stringValue = stringValue.substring(0, stringValue.length() - 1);
				}
			}
			data.addString(nRecord.getRow(), nRecord.getColumn(), stringValue);
		}
	}

	public void setFormatTrackingHSSFListener(FormatTrackingHSSFListener listener) {
		formatListener = listener;
	}
}
