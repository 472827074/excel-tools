package com.zhibitech.easyreport.tools.exceltool.impt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

import com.zhibitech.easyreport.tools.exceltool.data.ExcelData;

/**
 * 默认提供的excel数据读取实现
 * @author yumeng
 * @version  [版本号, 2016年6月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultExcelReader implements ExcelReader {

	public ExcelData readFile(FileInputStream file) throws IOException {
		POIFSFileSystem fs = new POIFSFileSystem(file);
		HSSFEventFactory factory = new HSSFEventFactory();
		HSSFRequest request = new HSSFRequest();
		ExcelData data = new ExcelData();
		request.addListener(new BOFRecordListener(data), BOFRecord.sid);
		SSTRecordListener stringListener = new SSTRecordListener(data);
		request.addListener(stringListener, SSTRecord.sid);
		request.addListener(stringListener, LabelSSTRecord.sid);
		NumbericListener numberListener = new NumbericListener(data);
		FormatTrackingHSSFListener fl = new FormatTrackingHSSFListener(numberListener);
		numberListener.setFormatTrackingHSSFListener(fl);
		request.addListenerForAllRecords(fl);

		factory.processWorkbookEvents(request, fs);
		return data;
	}

	public static void main(String args[]) {
		try {
			ExcelReader reader = new DefaultExcelReader();
			ExcelData data = reader.readFile(new FileInputStream("d:\\测试1.xls"));
			String as[][] = data.getSheetDatas(1);
			System.out.println(as[0]);
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
