package com.zhibitech.easyreport.tools.exceltool;

import java.io.File;
import java.io.IOException;

/**
 * excel数据读取
 * 
 * @author yumeng
 * @version  [版本号, 2016年6月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ExcelReader {

	public static final String EXCEL_PREFIX_XLS="xls";
	public static final String EXCEL_PREFIX_XLSX="xlsx";
	public ExcelData readFile(File file) throws IOException;

}
